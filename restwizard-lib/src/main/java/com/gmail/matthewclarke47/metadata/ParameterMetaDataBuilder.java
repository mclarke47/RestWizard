package com.gmail.matthewclarke47.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ParameterMetaDataBuilder {

    private Parameter parameter;
    private Field field;
    private String key;
    private Class<?> type;

    private Map<Class<? extends Annotation>, Supplier<ParameterMetaData>> annotationToMetaDataSubclass = new HashMap<>();

    private ParameterMetaDataBuilder(Parameter parameter){
        this.parameter = parameter;
    }

    private ParameterMetaDataBuilder(Field field){
        this.field = field;
    }

    public ParameterMetaDataBuilder key(String key) {
        this.key = key;
        return this;
    }

    public ParameterMetaDataBuilder type(Class<?> type) {
        this.type = type;
        return this;
    }

    public static ParameterMetaDataBuilder getBuilder(Parameter parameter){
        return new ParameterMetaDataBuilder(parameter);
    }

    public static ParameterMetaDataBuilder getBuilder(Field field){
        return new ParameterMetaDataBuilder(field);
    }

    public ParameterMetaData build() {

        annotationToMetaDataSubclass.put(JsonProperty.class, () -> new PropertyParameterMetaData(key, type));
        annotationToMetaDataSubclass.put(QueryParam.class, () -> new QueryPropertyMetaData(key, type));
        annotationToMetaDataSubclass.put(PathParam.class, () -> new PathPropertyMetaData(key, type));

        for(Class<? extends Annotation> clazz : annotationToMetaDataSubclass.keySet()){
            if(parameter != null){
                if(parameter.isAnnotationPresent(clazz)){
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
            }
            if(field != null){
                if(field.isAnnotationPresent(clazz)){
                    return annotationToMetaDataSubclass.get(clazz).get();
                }
            }
        }
        return null;
    }
}
