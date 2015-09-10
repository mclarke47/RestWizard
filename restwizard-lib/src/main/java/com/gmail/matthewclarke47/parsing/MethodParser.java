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

public class MethodParser {

    private Method method;

    public MethodParser(Method method) {

        this.method = method;
    }

    public List<ParameterMetaData> getParameterMetaDataList() {

        return Stream.concat(
                annotatedParameters(method.getParameters()).stream(),
                dtoParameters(method.getParameters()).stream())
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> annotatedParameters(Parameter[] params) {

        return Stream.of(params)
                .filter(this::parameterHasInterestingAnnotation)
                .map(this::parameterToParameterMetaData)
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> dtoParameters(Parameter[] params) {

        return Stream.of(params)
                .filter(this::parameterHasDtoObject)
                .map(this::dtoToMetaDataList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<ParameterMetaData> dtoToMetaDataList(Parameter parameter) {

        return Stream.of(parameter.getType().getDeclaredFields())
                .filter(this::fieldHasJsonProperty)
                .map(this::fieldToParameterMetaData)
                .collect(Collectors.toList());
    }

    private ParameterMetaData fieldToParameterMetaData(Field field) {

        return ParameterMetaData.builder(field).build();
    }

    private ParameterMetaData parameterToParameterMetaData(Parameter parameter) {

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

        return Arrays.asList(param.getType().getDeclaredFields())
                .stream()
                .anyMatch(field -> field.isAnnotationPresent(JsonProperty.class));
    }
}
