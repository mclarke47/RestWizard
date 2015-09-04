package com.gmail.matthewclarke47.metadata;

public class QueryParameterMetaData extends ParameterMetaData {
    public QueryParameterMetaData(String key, Class<?> type) {
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
}
