package com.gmail.matthewclarke47;

import java.util.List;

public class ResourceMetaData {

    private String path;
    private List<MethodMetaData> methodMetaDataList;

    public ResourceMetaData(String path, List<MethodMetaData> methodMetaDataList) {
        this.path = path;
        this.methodMetaDataList = methodMetaDataList;
    }

    public String getPath() {
        return path;
    }

    public List<MethodMetaData> getMethodMetaDataList() {
        return methodMetaDataList;
    }

    @Override
    public String toString() {

        String methodDataString = "\t";

        for(MethodMetaData methodMetaData : methodMetaDataList){
            methodDataString += methodMetaData.toString()+" \n\t";
        }


        return "Path: "+ path + "\nEndpoints: \n" + methodDataString;

    }
}
