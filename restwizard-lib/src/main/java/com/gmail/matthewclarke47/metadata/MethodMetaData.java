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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null || !(obj instanceof MethodMetaData))
            return false;

        MethodMetaData method = (MethodMetaData)obj;

        return this.pathSuffix.equals(method.getPathSuffix())
                && this.httpMethod.equals(method.getHttpMethod())
                && deepCheckParamsContains(method.getParameterMetaData());
    }

    private boolean deepCheckParamsContains(List<ParameterMetaData> parameterMetaDataList) {

        for(ParameterMetaData parameterMetaData : this.getParameterMetaData()){
            if(!parameterMetaDataList.contains(parameterMetaData)){
               return false;
            }
        }
        return true;
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
