package com.gmail.matthewclarke47.parsing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaDataBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParametersParser {

    private Method method;

    public ParametersParser(Method method) {

        this.method = method;
    }

    public List<ParameterMetaData> getMetaData() {

        return Stream.concat(
                annotatedParameters(method.getParameters()).stream(),
                dtoParameters(method.getParameters()).stream())
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> annotatedParameters(Parameter[] params) {

        return Stream.of(params)
                .filter(this::parameterHasInterestingAnnotation)
                .map(this::parameterToMetaData)
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> dtoParameters(Parameter[] params) {

        return Stream.of(params)
                .filter(this::parameterHasDtoObject)
                .map(this::dtoToMetaData)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> dtoToMetaData(Parameter parameter) {

        return Stream.of(parameter.getType().getDeclaredFields())
                .filter(this::fieldHasJsonProperty)
                .map(this::fieldToMetaData)
                .collect(Collectors.toList());
    }

    private ParameterMetaData fieldToMetaData(Field field) {

        String key = getAnnotationValueFromAnnotatedElement(field);
        Class<?> type = field.getType();

        return ParameterMetaDataBuilder.getBuilder(field).key(key).type(type).build();
    }

    private ParameterMetaData parameterToMetaData(Parameter parameter) {

        String key = getAnnotationValueFromAnnotatedElement(parameter);
        Class<?> type = parameter.getType();

        return ParameterMetaDataBuilder.getBuilder(parameter).key(key).type(type).build();
    }

    private boolean parameterHasInterestingAnnotation(Parameter param) {

        return WebServiceAnnotations.INTERESTING_ANNOTATIONS_TO_VALUE
                .keySet()
                .stream()
                .anyMatch(param::isAnnotationPresent);
    }

    private String getAnnotationValueFromAnnotatedElement(AnnotatedElement field) {

        return WebServiceAnnotations.INTERESTING_ANNOTATIONS_TO_VALUE.get(
                WebServiceAnnotations.INTERESTING_ANNOTATIONS_TO_VALUE.keySet()
                        .stream()
                        .filter(field::isAnnotationPresent)
                        .findFirst()
                        .get())
                .apply(field);
    }

    private boolean fieldHasJsonProperty(Field field) {

        return field.isAnnotationPresent(JsonProperty.class);
    }

    private boolean parameterHasDtoObject(Parameter param) {

        Class<?> paramType = param.getType();

        List<Field> fields = Arrays.asList(paramType.getDeclaredFields());

        return fields.stream().anyMatch(field -> field.isAnnotationPresent(JsonProperty.class));
    }
}
