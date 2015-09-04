package com.gmail.matthewclarke47.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceMetaData {

    private String path;

    private List<MethodMetaData> getMethods;
    private List<MethodMetaData> postMethods;
    private List<MethodMetaData> putMethods;
    private List<MethodMetaData> deleteMethods;

    public ResourceMetaData(String path, List<MethodMetaData> methodMetaDataList) {
        this.path = path;
        this.getMethods = sortMethods(methodMetaDataList, "GET");
        this.postMethods = sortMethods(methodMetaDataList, "POST");
        this.putMethods = sortMethods(methodMetaDataList, "PUT");
        this.deleteMethods = sortMethods(methodMetaDataList, "DELETE");
    }

    private List<MethodMetaData> sortMethods(List<MethodMetaData> methodMetaDataList, String httpMethod) {
        return methodMetaDataList.stream().filter(m -> m.getHttpMethod().equals(httpMethod)).collect(Collectors.toList());
    }

    public String getPath() {
        return path;
    }

    public List<MethodMetaData> getDeleteMethods() {
        return deleteMethods;
    }

    public List<MethodMetaData> getGetMethods() {
        return getMethods;
    }

    public List<MethodMetaData> getPostMethods() {
        return postMethods;
    }

    public List<MethodMetaData> getPutMethods() {
        return putMethods;
    }
}
