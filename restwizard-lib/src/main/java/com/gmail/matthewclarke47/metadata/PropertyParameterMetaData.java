package com.gmail.matthewclarke47.metadata;

public class PropertyParameterMetaData extends ParameterMetaData {

    public PropertyParameterMetaData(String key, Class<?> type) {
        super(key, type);
    }

    public String getKey() {
        return super.getKey();
    }

    public Class<?> getType() {
        return super.getType();
    }

    public String getValue() {
        return super.getValue();
    }

    @Override
    public String toString() {
        return getKey() + ": \"ABCXYZ\" ";
    }
}
