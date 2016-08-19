package io.dungdm93.wakeup.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

/**
 * Hibernate {@link UserType} implementation to handle JSON objects
 *
 * @see UserType
 */
public class JsonStringType implements UserType, ParameterizedType, Serializable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int[] SUPPORTED_SQL_TYPES = new int[]{Types.LONGVARCHAR, Types.CLOB, Types.BLOB};

    public static final String JAVA_TYPE = "javaType";
    public static final String SQL_TYPE = "sqlType";

    // before any guessing
    private Class<?> javaType = Object.class;
    private int sqlType = Types.LONGVARCHAR;

    @Override
    public void setParameterValues(Properties parameters) {
        String classTypeName = parameters.getProperty(JAVA_TYPE);
        try {
            this.javaType = ReflectHelper.classForName(classTypeName, this.getClass());
        } catch (ClassNotFoundException e) {
            throw new HibernateException("classType not found", e);
        }

        String type = parameters.getProperty(SQL_TYPE);
        if (type != null) {
            this.sqlType = Integer.decode(type);
        }
    }

    @Override
    public int[] sqlTypes() {
        return SUPPORTED_SQL_TYPES;
    }

    @Override
    public Class<?> returnedClass() {
        return this.javaType;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        Object obj = null;
        if (!rs.wasNull()) {
            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                byte[] bytes = rs.getBytes(names[0]);
                if (bytes != null) {
                    try {
                        obj = MAPPER.readValue(bytes, this.javaType);
                    } catch (IOException e) {
                        throw new HibernateException("unable to read object from result set", e);
                    }
                }
            } else {
                try {
                    String content = rs.getString(names[0]);
                    if (content != null) {
                        obj = MAPPER.readValue(content, this.javaType);
                    }
                } catch (IOException e) {
                    throw new HibernateException("unable to read object from result set", e);
                }
            }
        }
        return obj;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, this.sqlType);
        } else {
            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                try {
                    st.setBytes(index, MAPPER.writeValueAsBytes(value));
                } catch (JsonProcessingException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            } else {
                try {
                    st.setString(index, MAPPER.writeValueAsString(value));
                } catch (JsonProcessingException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) return null;
        try {
            return MAPPER.readValue(MAPPER.writeValueAsString(value), this.javaType);
        } catch (IOException e) {
            throw new HibernateException("unable to deep copy object", e);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new HibernateException("unable to disassemble object", e);
        }
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }
}
