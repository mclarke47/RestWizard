package com.gmail.matthewclarke47.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.WebServiceAnnotations;
import com.gmail.matthewclarke47.formatting.TypeInferringExampleGenerator;
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
        this.value = TypeInferringExampleGenerator.getExample(type);
    }

    public static ParameterMetaDataBuilder builder(Field field) {
        return new ParameterMetaDataBuilder(field);
    }

    public static ParameterMetaDataBuilder builder(Parameter parameter) {
        return new ParameterMetaDataBuilder(parameter);
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public Class<?> getType() {
        return type;
    }

    public static class ParameterMetaDataBuilder {

        private Optional<AnnotatedElement> parameter = Optional.empty();
        private Optional<AnnotatedElement> field = Optional.empty();
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

            //// FIXME: 10/09/2015 make nicer
            for (Class<? extends Annotation> clazz : annotationToMetaDataSubclass.keySet()) {
                if (isAnnotationPresent(clazz, parameter)) {
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
                if (isAnnotationPresent(clazz, field)) {
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
            }
            return null;
        }

        private boolean isAnnotationPresent(Class<? extends Annotation> clazz, Optional<AnnotatedElement> element) {
            return element.isPresent() && element.get().isAnnotationPresent(clazz);
        }
    }
}
