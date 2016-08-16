package io.dungdm93.wakeup.type;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

public class JsonBinaryType
        extends AbstractSingleColumnStandardBasicType<Object>
        implements DynamicParameterizedType {

    public JsonBinaryType() {
        super(
                JsonBinarySqlTypeDescriptor.INSTANCE,   // sqlTypeDescriptor
                new JsonJavaTypeDescriptor()            // javaTypeDescriptor
        );
    }

    public String getName() {
        return "jsonb";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonJavaTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
