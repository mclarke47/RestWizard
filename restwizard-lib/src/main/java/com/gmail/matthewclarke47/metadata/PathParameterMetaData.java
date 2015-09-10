package com.gmail.matthewclarke47.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class PathParameterMetaData extends ParameterMetaData {
    public PathParameterMetaData(String key, Class<?> type) {
        super(key, type);
    }

    public static PathParameterMetaData castTo(ParameterMetaData parameterMetaData){
        return (PathParameterMetaData) parameterMetaData;
    }

    public static List<PathParameterMetaData> castTo(List<ParameterMetaData> parameterMetaData){
        return parameterMetaData.stream()
                .filter(paramData -> paramData instanceof PathParameterMetaData)
                .map(PathParameterMetaData::castTo)
                .collect(Collectors.toList());
    }
}
