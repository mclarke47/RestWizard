package com.gmail.matthewclarke47.metadata;

public abstract class ParameterMetaData {
    private String key;
    private Class<?> type;

    public ParameterMetaData(String key, Class<?> type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public Class<?> getType() {
        return type;
    }
}
