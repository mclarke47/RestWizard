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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ParameterMetaData))
            return false;

        ParameterMetaData method = (ParameterMetaData) obj;

        return this.key.equals(method.getKey())
                && this.type.equals(method.getType());
    }
}
