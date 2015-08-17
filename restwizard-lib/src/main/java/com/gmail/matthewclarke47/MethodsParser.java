package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.google.common.collect.Lists;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MethodsParser {

    private static final Map<Class<? extends Annotation>, String> METHOD_TO_TEXT = new HashMap<>();

    static {
        METHOD_TO_TEXT.put(GET.class, HttpMethod.GET);
        METHOD_TO_TEXT.put(POST.class, HttpMethod.POST);
        METHOD_TO_TEXT.put(PUT.class, HttpMethod.PUT);
        METHOD_TO_TEXT.put(DELETE.class, HttpMethod.DELETE);
    }

    private Class<?> clazz;

    public MethodsParser(Class<?>clazz) {

        this.clazz = clazz;
    }

    private List<MethodMetaData> parse(){

        List<MethodMetaData> methodMetaData = Lists.newArrayList();

        for (Method method : clazz.getDeclaredMethods()) {
            if (methodHasHttpMethod(method)) {

                List<ParameterMetaData> parameterMetaData = parseParameterMetaData(method);

                methodMetaData.add(new MethodMetaData(getHttpMethod(method), method.getAnnotation(Path.class).value(), parameterMetaData));
            }
        }

        return methodMetaData;

    }

    private String getHttpMethod(Method method){

        return METHOD_TO_TEXT
                .getOrDefault(METHOD_TO_TEXT
                        .keySet()
                        .stream()
                        .filter(method::isAnnotationPresent)
                        .findFirst().get(), "");
    }

    private boolean methodHasHttpMethod(Method method) {
        return METHOD_TO_TEXT
                        .keySet()
                        .stream()
                        .anyMatch(method::isAnnotationPresent);
    }

    private List<ParameterMetaData> parseParameterMetaData(Method method) {

        return new ParametersParser(method).getMetaData();
    }

    public List<MethodMetaData> getMetaData() {
        return parse();
    }
}
