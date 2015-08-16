package com.gmail.matthewclarke47.metadata;

import java.util.List;

public class MethodMetaData {

    private String httpMethod;
    private String pathSuffix;
    private List<ParameterMetaData> parameterMetaData;

    public MethodMetaData(String httpMethod, String pathSuffix, List<ParameterMetaData> parameterMetaData) {
        this.httpMethod = httpMethod;
        this.pathSuffix = pathSuffix;
        this.parameterMetaData = parameterMetaData;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPathSuffix() {
        return pathSuffix;
    }

    public List<ParameterMetaData> getParameterMetaData() {
        return parameterMetaData;
    }

    @Override
    public String toString() {

        return MethodPrinter.print(this);
    }
}
