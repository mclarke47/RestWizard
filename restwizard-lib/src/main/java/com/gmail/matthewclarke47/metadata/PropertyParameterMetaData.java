package com.gmail.matthewclarke47.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class PropertyParameterMetaData extends ParameterMetaData {

    public PropertyParameterMetaData(String key, Class<?> type) {
        super(key, type);
    }

    public static PropertyParameterMetaData castTo(ParameterMetaData parameterMetaData) {
        return (PropertyParameterMetaData) parameterMetaData;
    }

    public static List<PropertyParameterMetaData> castTo(List<ParameterMetaData> parameterMetaData) {
        return parameterMetaData.stream()
                .filter(paramData -> paramData instanceof PropertyParameterMetaData)
                .map(PropertyParameterMetaData::castTo)
                .collect(Collectors.toList());
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
