package com.gmail.matthewclarke47.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class ParameterMetaData {
    private String key;



    private String value;
    private Class<?> type;

    public ParameterMetaData(String key, Class<?> type) {
        this.key = key;
        this.type = type;
        this.value = formatExample(type);
    }

    private String formatExample(Class<?> type) {

        if (type.equals(boolean.class)) {
            return "true";
        } else if (type.equals(int.class)) {
            return "12345";
        }
        return "\"ABC\"";
    }

    public String getValue() {
        return value;
    }

    public static ParameterMetaDataBuilder builder(Field field) {
        return new ParameterMetaDataBuilder(field);
    }

    public static ParameterMetaDataBuilder builder(Parameter parameter) {
        return new ParameterMetaDataBuilder(parameter);
    }

    public String getKey() {
        return key;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ParameterMetaData))
            return false;

        ParameterMetaData method = (ParameterMetaData) obj;

        return this.key.equals(method.getKey())
                && this.type.equals(method.getType());
    }

    public static class ParameterMetaDataBuilder {

        private Optional<Parameter> parameter = Optional.empty();
        private Optional<Field> field = Optional.empty();
        private String key;
        private Class<?> type;

        private Map<Class<? extends Annotation>, Supplier<ParameterMetaData>> annotationToMetaDataSubclass =
                ImmutableMap.<Class<? extends Annotation>, Supplier<ParameterMetaData>>builder()
                        .put(JsonProperty.class, () -> new PropertyParameterMetaData(key, type))
                        .put(QueryParam.class, () -> new QueryParameterMetaData(key, type))
                        .put(PathParam.class, () -> new PathParameterMetaData(key, type))
                        .build();

        private ParameterMetaDataBuilder(Parameter parameter) {
            this.parameter = Optional.of(parameter);
            this.key = getAnnotationValueFromAnnotatedElement(parameter);
            this.type = parameter.getType();
        }

        private ParameterMetaDataBuilder(Field field) {
            this.field = Optional.of(field);
            this.key = getAnnotationValueFromAnnotatedElement(field);
            this.type = field.getType();

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

        public ParameterMetaData build() {

            for (Class<? extends Annotation> clazz : annotationToMetaDataSubclass.keySet()) {
                if (parameter.isPresent()) {
                    if (parameter.get().isAnnotationPresent(clazz)) {
                        return annotationToMetaDataSubclass.get(clazz).get();
                    }
                }
                if (field.isPresent()) {
                    if (field.get().isAnnotationPresent(clazz)) {
                        return annotationToMetaDataSubclass.get(clazz).get();
                    }
                }
            }
            return null;
        }
    }
}
