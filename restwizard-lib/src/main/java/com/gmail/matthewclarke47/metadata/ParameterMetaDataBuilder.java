package com.gmail.matthewclarke47.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ParameterMetaDataBuilder {

    private Optional<Parameter> parameter = Optional.empty();
    private Optional<Field> field = Optional.empty();
    private String key;
    private Class<?> type;

    private Map<Class<? extends Annotation>, Supplier<ParameterMetaData>> annotationToMetaDataSubclass =
            ImmutableMap.<Class<? extends Annotation>, Supplier<ParameterMetaData>>builder()
                    .put(JsonProperty.class, () -> new PropertyParameterMetaData(key, type))
                    .put(QueryParam.class, () -> new QueryPropertyMetaData(key, type))
                    .put(PathParam.class, () -> new PathPropertyMetaData(key, type))
                    .build();

    private ParameterMetaDataBuilder(Parameter parameter) {
        this.parameter = Optional.of(parameter);
    }

    private ParameterMetaDataBuilder(Field field) {
        this.field = Optional.of(field);
    }

    public static ParameterMetaDataBuilder getBuilder(Parameter parameter) {
        return new ParameterMetaDataBuilder(parameter);
    }

    public static ParameterMetaDataBuilder getBuilder(Field field) {
        return new ParameterMetaDataBuilder(field);
    }

    public ParameterMetaDataBuilder key(String key) {
        this.key = key;
        return this;
    }

    public ParameterMetaDataBuilder type(Class<?> type) {
        this.type = type;
        return this;
    }

    public ParameterMetaData build() {

        for(Class<? extends Annotation> clazz : annotationToMetaDataSubclass.keySet()){
            if(parameter.isPresent()){
                if(parameter.get().isAnnotationPresent(clazz)){
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
            }
            if(field.isPresent()){
                if(field.get().isAnnotationPresent(clazz)){
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
            }
        }
        return null;
    }
}
