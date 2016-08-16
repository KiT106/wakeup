package io.dungdm93.wakeup.type;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

public class JsonStringType
        extends AbstractSingleColumnStandardBasicType<Object>
        implements DynamicParameterizedType {

    public JsonStringType() {
        super(
                JsonStringSqlTypeDescriptor.INSTANCE,   // sqlTypeDescriptor
                new JsonJavaTypeDescriptor()            // javaTypeDescriptor
        );
    }

    public String getName() {
        return "json";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonJavaTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
