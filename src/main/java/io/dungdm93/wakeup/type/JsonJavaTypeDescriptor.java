package io.dungdm93.wakeup.type;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

public class JsonJavaTypeDescriptor
        extends AbstractTypeDescriptor<Object>
        implements DynamicParameterizedType {

    public JsonJavaTypeDescriptor() {
        super(Object.class, new JsonMutabilityPlan());
    }

    @Override
    public void setParameterValues(Properties parameters) {
        Class<?> jsonObjectClass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
        jackson().setObjectType(jsonObjectClass);
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        JsonNode oneNode = jackson().toJsonNode(one);
        JsonNode anotherNode = jackson().toJsonNode(another);
        return oneNode.equals(anotherNode);
    }

    @Override
    public String toString(Object value) {
        return jackson().toString(value);
    }

    @Override
    public Object fromString(String string) {
        return jackson().fromString(string);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        if (Object.class.isAssignableFrom(type)) {
            return (X) jackson().toJsonNode(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return fromString(value.toString());
    }

    private JsonMutabilityPlan jackson() {
        return (JsonMutabilityPlan) getMutabilityPlan();
    }
}