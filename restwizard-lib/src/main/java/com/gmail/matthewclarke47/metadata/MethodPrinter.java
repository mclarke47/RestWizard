package com.gmail.matthewclarke47.metadata;

import java.util.List;
import java.util.stream.Collectors;

class MethodPrinter {

    private MethodMetaData methodMetaData;

    public MethodPrinter(MethodMetaData methodMetaData) {

        this.methodMetaData = methodMetaData;
    }

    public static String print(MethodMetaData methodMetaData) {
        return new MethodPrinter(methodMetaData).print();
    }

    private String formatPath(String pathSuffix, List<ParameterMetaData> queryStringParams) {
        if (queryStringParams.isEmpty()) {
            return pathSuffix;
        }

        String str = pathSuffix + "?";

        for (ParameterMetaData queryStringParam : queryStringParams) {
            if (!str.equals(pathSuffix + "?")) {
                str += "&";
            }
            str += queryStringParam.getKey() + "=ABC";
        }
        return str;
    }

    private String formatJson(List<ParameterMetaData> propertyStringParams) {

        if (propertyStringParams.isEmpty()) {
            return "";
        }

        String str = "\n{\n\t";

        for (ParameterMetaData queryStringParam : propertyStringParams) {
            if (!str.equals("\n{\n\t")) {
                str += ",\n\t";
            }
            str += "\"" + queryStringParam.getKey() + "\": \"ABC\"";
        }
        str += "\n}";
        return str;

    }

    private String print() {

        List<ParameterMetaData> queryString = methodMetaData
                .getParameterMetaData()
                .stream()
                .filter(parameterMetaData -> parameterMetaData instanceof QueryParameterMetaData)
                .collect(Collectors.toList());

        String str = "httpMethod " + methodMetaData.getHttpMethod() + " pathSuffix: " + formatPath(methodMetaData.getPathSuffix(), queryString);

        str += formatJson(methodMetaData
                .getParameterMetaData()
                .stream()
                .filter(parameterMetaData -> parameterMetaData instanceof PropertyParameterMetaData)
                .collect(Collectors.toList()));

        return str;
    }
}
