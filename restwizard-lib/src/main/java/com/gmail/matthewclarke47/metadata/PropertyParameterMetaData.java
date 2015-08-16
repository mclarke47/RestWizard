package com.gmail.matthewclarke47.metadata;

public class PropertyParameterMetaData extends ParameterMetaData{

    public PropertyParameterMetaData(String key, Class<?> type) {
        super(key, type);
    }

    @Override
    public String toString() {
        return getKey() +": \"ABCXYZ\" " ;
    }
}
