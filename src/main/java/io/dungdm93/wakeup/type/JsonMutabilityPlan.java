package io.dungdm93.wakeup.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonMutabilityPlan extends MutableMutabilityPlan<Object> {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private JavaType type;

    public void setObjectType(Type type) {
        this.type = OBJECT_MAPPER.getTypeFactory().constructType(type);
    }

    public Object fromString(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + json + " cannot be transformed to JSON object");
        }
    }

    public String toString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + object + " cannot be transformed to a String");
        }
    }

    public JsonNode toJsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JsonNode toJsonNode(Object object) {
        return toJsonNode(toString(object));
    }

    @Override
    protected Object deepCopyNotNull(Object value) {
        return fromString(toString(value));
    }
}
