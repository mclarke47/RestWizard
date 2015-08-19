package com.gmail.matthewclarke47.formatting.html;

import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class DocsPage {

    private String header = "<head><meta charset=\"utf-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">\n" +
            "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>\n" +
            "  <script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script></head>";

    private String end = "</div></body></html>";

    public DocsPage(String pageTitle) {
        html = new StringBuffer("<!DOCTYPE html>\n" +
                "<html lang=\"en\">"+header+"<body><div class=\"container\"><div class=\"page-header\"><h1>"+pageTitle+"</h1></div>");
    }

    private StringBuffer html;

    public void addResource(ResourceMetaData resourceMetaData){
        html.append(resourceToHtml(resourceMetaData));
    }

    public String build(){
        return html.append(end).toString();
    }

    private String resourceToHtml(ResourceMetaData resourceMetaData) {

        BootstrapLayout bootstrapLayout = new BootstrapLayout();

        BootstrapRow row = bootstrapLayout.row(resourceMetaData.getPath());

        for (MethodMetaData metaData : resourceMetaData.getMethodMetaDataList()) {
            row.addCol(methodToHtml(resourceMetaData, metaData));
        }

        return bootstrapLayout.toString();
    }

    private final Map<String, String> HTTP_METHOD_TO_STYLE =
            ImmutableMap.<String, String>builder()
                    .put("GET", "success")
                    .put("POST", "primary")
                    .put("PUT", "info")
                    .build();

    private String methodToHtml(ResourceMetaData resourceMetaData, MethodMetaData metaData) {

        String style = HTTP_METHOD_TO_STYLE.get(metaData.getHttpMethod());

        String str = "<div class=\"panel panel-"+style+"\">";
        str += "<div class=\"panel-heading\">";
        str += "<strong>"+metaData.getHttpMethod() + "</strong> " + metaData.getPathSuffix();
        str += "</div>";
        str += " <div class=\"panel-body\">" +formatJson(metaData) + "</div>";
        str += "</div>";
        return str;
    }

    private String formatJson(MethodMetaData methodParams) {

        if (methodParams.getParameterMetaData().isEmpty()) {
            return "";
        }

        String str = "<h4>Request example:</h4><pre>\n{\n\t";

        for (ParameterMetaData property : methodParams.getParameterMetaData()) {
            if (!str.equals("<h4>Request example:</h4><pre>\n{\n\t")) {
                str += ",\n\t";
            }
            str += "\"" + property.getKey() + "\": " + formatExample(property.getType());
        }
        str += "\n}</pre>";
        return str;

    }

    private String formatExample(Class<?> type) {

        if (type.equals(boolean.class)) {
            return "true";
        } else if (type.equals(int.class)) {
            return "12345";
        }
        return "\"ABC\"";
    }
}
