package com.gmail.matthewclarke47.metadata;

import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.formatting.ClassToJsonFormatter;
import com.gmail.matthewclarke47.formatting.JsonFormatter;
import com.gmail.matthewclarke47.parsing.MethodParser;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.List;

public class MethodMetaData {

    private String httpMethod;
    private String pathSuffix;
    private List<ParameterMetaData> parameterMetaData;
    private List<QueryParameterMetaData> queryParams;
    private List<PathParameterMetaData> pathParams;
    private String requestExample;
    private String responseExample;

    public MethodMetaData(String httpMethod, String pathSuffix, List<ParameterMetaData> parameterMetaData, Class<?> returnType) {
        this.httpMethod = httpMethod;
        this.pathSuffix = pathSuffix;
        this.parameterMetaData = parameterMetaData;
        this.queryParams = QueryParameterMetaData.castTo(parameterMetaData);
        this.pathParams = PathParameterMetaData.castTo(parameterMetaData);
        this.requestExample = createRequestExample(parameterMetaData);
        this.responseExample = createResponseExample(returnType);
    }

    public static MethodMetaDataBuilder builder(Method method) {
        return new MethodMetaDataBuilder(method);
    }

    public List<QueryParameterMetaData> getQueryParams() {
        return queryParams;
    }

    public List<PathParameterMetaData> getPathParams() {
        return pathParams;
    }

    public String getRequestExample() {
        return requestExample;
    }

    private String createRequestExample(List<ParameterMetaData> parameterMetaData) {

        return new JsonFormatter(PropertyParameterMetaData.castTo(parameterMetaData)).format();

    }

    private String createResponseExample(Class<?> returnType) {

        return new ClassToJsonFormatter(returnType).format();

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

    public String getResponseExample() {
        return responseExample;
    }

    public void setResponseExample(String responseExample) {
        this.responseExample = responseExample;
    }

    public static class MethodMetaDataBuilder {

        private final Class<?> returnType;
        private String httpMethod;
        private String pathSuffix;
        private List<ParameterMetaData> parameterMetaData;

        private MethodMetaDataBuilder(Method method) {
            this.pathSuffix = getPathSuffix(method);
            this.httpMethod = getHttpMethod(method);
            this.parameterMetaData = parseParameterMetaData(method);
            this.returnType = method.getReturnType();
        }

        private String getPathSuffix(Method method) {
            if (method.isAnnotationPresent(Path.class)) {
                return method.getAnnotation(Path.class).value();
            } else {
                return "";
            }
        }

        private String getHttpMethod(Method method) {

            return WebServiceAnnotations.HTTP_METHOD_TO_TEXT
                    .getOrDefault(WebServiceAnnotations.HTTP_METHOD_TO_TEXT
                            .keySet()
                            .stream()
                            .filter(method::isAnnotationPresent)
                            .findFirst().get(), "");
        }

        private List<ParameterMetaData> parseParameterMetaData(Method method) {

            return new MethodParser(method).getParameterMetaDataList();
        }

        public MethodMetaData build() {
            return new MethodMetaData(this.httpMethod, this.pathSuffix, this.parameterMetaData, this.returnType);
        }
    }
}
