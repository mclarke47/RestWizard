package com.gmail.matthewclarke47.parsing;

import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.metadata.MethodMetaData;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassParser {

    private Class<?> clazz;

    public ClassParser(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<MethodMetaData> getMethodMetaDataList() {

        return Stream.of(clazz.getDeclaredMethods())
                .filter(this::isHttpMethod)
                .map(this::methodToMethodMetaData)
                .collect(Collectors.toList());
    }

    private boolean isHttpMethod(Method method) {
        return WebServiceAnnotations.HTTP_METHOD_TO_TEXT
                .keySet()
                .stream()
                .anyMatch(method::isAnnotationPresent);
    }

    private MethodMetaData methodToMethodMetaData(Method method) {

        return MethodMetaData
                .builder(method)
                .build();
    }
}
