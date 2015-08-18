package com.gmail.matthewclarke47.metadata;

import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.parsing.ParametersParser;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
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

    public static MethodMetaDataBuilder builder(Method method) {
        return new MethodMetaDataBuilder(method);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof MethodMetaData))
            return false;

        MethodMetaData method = (MethodMetaData) obj;

        return this.pathSuffix.equals(method.getPathSuffix())
                && this.httpMethod.equals(method.getHttpMethod())
                && this.getParameterMetaData().size() == method.getParameterMetaData().size()
                && deepCheckParamsContains(method.getParameterMetaData());
    }

    private boolean deepCheckParamsContains(List<ParameterMetaData> parameterMetaDataList) {

        for (ParameterMetaData parameterMetaData : this.getParameterMetaData()) {
            if (!parameterMetaDataList.contains(parameterMetaData)) {
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

    public static class MethodMetaDataBuilder {

        private String httpMethod;
        private String pathSuffix;
        private List<ParameterMetaData> parameterMetaData;

        private MethodMetaDataBuilder(Method method) {
            this.pathSuffix = method.getAnnotation(Path.class).value();
            this.httpMethod = getHttpMethod(method);
            this.parameterMetaData = parseParameterMetaData(method);
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

            return new ParametersParser(method).getMetaData();
        }

        public MethodMetaData build() {
            return new MethodMetaData(this.httpMethod, this.pathSuffix, this.parameterMetaData);
        }
    }
}
