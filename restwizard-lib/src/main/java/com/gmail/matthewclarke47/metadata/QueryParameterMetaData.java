package com.gmail.matthewclarke47.metadata;

import java.util.List;
import java.util.stream.Collectors;

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

    public static QueryParameterMetaData castTo(ParameterMetaData parameterMetaData){
        return (QueryParameterMetaData) parameterMetaData;
    }

    public static List<QueryParameterMetaData> castTo(List<ParameterMetaData> parameterMetaData){
        return parameterMetaData.stream()
                .filter(paramData -> paramData instanceof QueryParameterMetaData)
                .map(QueryParameterMetaData::castTo)
                .collect(Collectors.toList());
    }
}
