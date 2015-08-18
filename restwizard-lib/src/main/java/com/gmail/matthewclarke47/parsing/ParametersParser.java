package com.gmail.matthewclarke47.parsing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;

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

        return ParameterMetaData.builder(field).build();
    }

    private ParameterMetaData parameterToMetaData(Parameter parameter) {

        return ParameterMetaData.builder(parameter).build();
    }

    private boolean parameterHasInterestingAnnotation(Parameter param) {

        return WebServiceAnnotations.INTERESTING_ANNOTATIONS_TO_VALUE
                .keySet()
                .stream()
                .anyMatch(param::isAnnotationPresent);
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
