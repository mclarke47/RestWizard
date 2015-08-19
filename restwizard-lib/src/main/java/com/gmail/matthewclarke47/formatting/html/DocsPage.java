package com.gmail.matthewclarke47.formatting.html;

import com.gmail.matthewclarke47.metadata.*;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        str += "<strong>"+metaData.getHttpMethod() + "</strong> " + metaData.getPathSuffix() + getQueryParamIfPresent(metaData);
        str += "</div>";
        str += " <div class=\"panel-body\">" +formatMetadataBody(metaData) + "</div>";
        str += "</div>";
        return str;
    }

    private String formatMetadataBody(MethodMetaData metaData){
        BootstrapLayout bootstrapLayout = new BootstrapLayout();

        BootstrapRow queryParamTable = bootstrapLayout.row();
        queryParamTable.addCol(queryParamTable(metaData));

        BootstrapRow pathParamTable = bootstrapLayout.row();
        pathParamTable.addCol(pathParamTable(metaData));

        BootstrapRow jsonRow = bootstrapLayout.row();
        jsonRow.addCol(formatJson(metaData));

        return bootstrapLayout.toString();
    }

    private String queryParamTable(MethodMetaData metaData) {
        if( metaData.getParameterMetaData().stream().anyMatch(parameterMetaData -> parameterMetaData instanceof QueryParameterMetaData))
        {
            List<QueryParameterMetaData> queryStringParams = metaData.getParameterMetaData().stream()
                    .filter(parameterMetaData -> parameterMetaData instanceof QueryParameterMetaData)
                    .map(parameterMetaData -> (QueryParameterMetaData) parameterMetaData)
                    .collect(Collectors.toList());
            String tStart = "<h4>Query parameters</h4><table class=\"table table-striped\">\n" +
                    "    <thead>\n" +
                    "      <tr>\n" +
                    "        <th>Query Parameter Key</th>\n" +
                    "        <th>Query Parameter example value</th>\n" +
                    "      </tr>\n" +
                    "    </thead><tbody>";
            String tEnd = "</tbody>\n" +
                    "  </table>";

            String tContents ="";

            for (QueryParameterMetaData queryStringParam : queryStringParams) {
                tContents += "<tr>\n" +
                        "        <td>"+queryStringParam.getKey()+"</td>\n" +
                        "        <td>ABC</td>\n" +
                        "      </tr>";
            }



            return tStart + tContents + tEnd;
        }
        return "";
    }

    private String pathParamTable(MethodMetaData metaData) {
        if( metaData.getParameterMetaData().stream().anyMatch(parameterMetaData -> parameterMetaData instanceof PathParameterMetaData))
        {
            List<PathParameterMetaData> pathStringParams = metaData.getParameterMetaData().stream()
                    .filter(parameterMetaData -> parameterMetaData instanceof PathParameterMetaData)
                    .map(parameterMetaData -> (PathParameterMetaData) parameterMetaData)
                    .collect(Collectors.toList());
            String tStart = "<h4>Path parameters</h4><table class=\"table table-striped\">\n" +
                    "    <thead>\n" +
                    "      <tr>\n" +
                    "        <th>Path Parameter Key</th>\n" +
                    "        <th>Path Parameter example value</th>\n" +
                    "      </tr>\n" +
                    "    </thead><tbody>";
            String tEnd = "</tbody>\n" +
                    "  </table>";

            String tContents ="";

            for (PathParameterMetaData pathStringParam : pathStringParams) {
                tContents += "<tr>\n" +
                        "        <td>"+pathStringParam.getKey()+"</td>\n" +
                        "        <td>ABC</td>\n" +
                        "      </tr>";
            }



            return tStart + tContents + tEnd;
        }
        return "";
    }

    private String getQueryParamIfPresent(MethodMetaData metaData) {
        if( metaData.getParameterMetaData().stream().anyMatch(parameterMetaData -> parameterMetaData instanceof QueryParameterMetaData))
        {
            List<QueryParameterMetaData> queryStringParams = metaData.getParameterMetaData().stream()
                    .filter(parameterMetaData -> parameterMetaData instanceof QueryParameterMetaData)
                    .map(parameterMetaData -> (QueryParameterMetaData) parameterMetaData)
                    .collect(Collectors.toList());
            String str = "?";

            for (QueryParameterMetaData queryStringParam : queryStringParams) {
                if (!str.equals("?")) {
                    str += "&";
                }
                str += queryStringParam.getKey() + "=ABC";
            }
            return str;
        }
        return "";
    }

    private String formatJson(MethodMetaData methodParams) {

        if (containsJsonData(methodParams)) {
            List<PropertyParameterMetaData> propertyParams = methodParams.getParameterMetaData().stream()
                    .filter(parameterMetaData -> parameterMetaData instanceof PropertyParameterMetaData)
                    .map(parameterMetaData -> (PropertyParameterMetaData) parameterMetaData)
                    .collect(Collectors.toList());

            String str = "<h4>Request example:</h4><pre>\n{\n\t";

            for (PropertyParameterMetaData property : propertyParams) {
                if (!str.equals("<h4>Request example:</h4><pre>\n{\n\t")) {
                    str += ",\n\t";
                }
                str += "\"" + property.getKey() + "\": " + formatExample(property.getType());
            }
            str += "\n}</pre>";
            return str;
        }
        else {
            return "";
        }

    }

    private boolean containsJsonData(MethodMetaData methodParams){
        return !methodParams.getParameterMetaData().isEmpty() && methodParams.getParameterMetaData().stream()
                .anyMatch(parameterMetaData -> parameterMetaData instanceof PropertyParameterMetaData);

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
