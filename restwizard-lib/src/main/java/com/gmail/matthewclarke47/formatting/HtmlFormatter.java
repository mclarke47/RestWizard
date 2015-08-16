package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import java.io.FileWriter;
import java.util.List;

public class HtmlFormatter implements DocsFormatter{
    @Override
    public void print(List<ResourceMetaData> resourceMetaData) {
        String str = "<html><head></head><body>";

        for(ResourceMetaData rmd : resourceMetaData){
            str += resourceToHtml(rmd);
        }

        str += "</body></html>";

        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter("./doc.html");
            fileWriter.append(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            //Ignore for now
        }

    }

    private String resourceToHtml(ResourceMetaData resourceMetaData) {
        String str = "<hr>";

        for (MethodMetaData metaData : resourceMetaData.getMethodMetaDataList())
            str += methodToHtml(resourceMetaData, metaData);

        return str;
    }

    private String methodToHtml(ResourceMetaData resourceMetaData, MethodMetaData metaData) {
        String str = "";
        str += "<h1>";
        str += resourceMetaData.getPath() + metaData.getPathSuffix();
        str += "</h1>";
        str += "<h2>";
        str += metaData.getHttpMethod();
        str += "</h2>";
        str += "<h3>";
        str += formatJson(metaData);
        str += "</h3>";
        return str;
    }

    private String formatJson(MethodMetaData methodParams){

        if(methodParams.getParameterMetaData().isEmpty()){
            return "";
        }

        String str = "\n{\n\t";

        for(ParameterMetaData property : methodParams.getParameterMetaData()){
            if(!str.equals("\n{\n\t")){
                str +=",\n\t";
            }
            str += "\""+ property.getKey() + "\": \"ABC\"";
        }
        str += "\n}";
        return str;

    }
}
