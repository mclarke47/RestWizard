package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.ParameterMetaData;
import com.gmail.matthewclarke47.ResourceMetaData;

import java.io.FileWriter;
import java.util.List;

public class HtmlFormatter implements DocsFormatter{
    @Override
    public void print(ResourceMetaData resourceMetaData) {
        String str = "<html><head></head><body>";
        str += "<h1>";
        str += resourceMetaData.getPath() + resourceMetaData.getMethodMetaDataList().get(0).getPathSuffix();
        str += "</h1>";
        str += "<h2>";
        str += resourceMetaData.getMethodMetaDataList().get(0).getHttpMethod();
        str += "</h2>";
        str += "<h3>";
        str += formatJson(resourceMetaData.getMethodMetaDataList().get(0).getParameterMetaData());
        str += "</h3>";

        str += "</body></html>";

        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter("./doc.html");
            fileWriter.append(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
        }

    }

    private String formatJson(List<ParameterMetaData> propertyStringParams){

        if(propertyStringParams.isEmpty()){
            return "";
        }

        String str = "\n{\n\t";

        for(ParameterMetaData queryStringParam : propertyStringParams){
            if(!str.equals("\n{\n\t")){
                str +=",\n\t";
            }
            str += "\""+ queryStringParam.getKey() + "\": \"ABC\"";
        }
        str += "\n}";
        return str;

    }
}
