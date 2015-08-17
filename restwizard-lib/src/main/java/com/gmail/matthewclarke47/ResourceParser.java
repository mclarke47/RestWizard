package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaDataBuilder;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

class ResourceParser {

    private Object obj;
    private Optional<ResourceMetaData> metaData;

    private static final Map<Class<? extends Annotation>, String> METHOD_TO_TEXT_MAP = new HashMap<>();

    public static final Map<Class<? extends Annotation>, Function<AnnotatedElement, String>> INTERESTING_ANNOTATIONS =
            new HashMap<>();

    static {
        METHOD_TO_TEXT_MAP.put(GET.class, HttpMethod.GET);
        METHOD_TO_TEXT_MAP.put(POST.class, HttpMethod.POST);
        METHOD_TO_TEXT_MAP.put(PUT.class, HttpMethod.PUT);
        METHOD_TO_TEXT_MAP.put(DELETE.class, HttpMethod.DELETE);

        INTERESTING_ANNOTATIONS.put(JsonProperty.class, element -> element.getAnnotation(JsonProperty.class).value());
        INTERESTING_ANNOTATIONS.put(QueryParam.class, element -> element.getAnnotation(QueryParam.class).value());
        INTERESTING_ANNOTATIONS.put(PathParam.class, element -> element.getAnnotation(PathParam.class).value());
    }

    public ResourceParser(Object obj) {
        this.obj = obj;
        this.metaData = document(obj);
    }

    private boolean isResource() {
        return obj.getClass().isAnnotationPresent(Path.class);
    }

    public Optional<ResourceMetaData> getMetaData() {
        return metaData;
    }

    private Optional<ResourceMetaData> document(Object obj) {

        if(isResource()) {
            Class<?> clazz = obj.getClass();

            List<MethodMetaData> methodMetaData = new ArrayList<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (methodHasHttpMethod(method)) {

                    List<ParameterMetaData> parameterMetaData = parseParameterMetaData(method);

                    methodMetaData.add(new MethodMetaData(getHttpMethod(method), method.getAnnotation(Path.class).value(), parameterMetaData));
                }
            }

            return Optional.of(new ResourceMetaData(clazz.getAnnotation(Path.class).value(), methodMetaData));
        }
        return Optional.empty();

    }

    private List<ParameterMetaData> parseParameterMetaData(Method method) {

        List<ParameterMetaData> parameterMetaData = new ArrayList<>();

        Parameter[] params = method.getParameters();

        for(Parameter param : params){
            if(parameterHasInterestingAnnotation(param)){
                String key = getAnnotationValueFromParameter(param);
                Class<?> type = param.getType();

                parameterMetaData.add(ParameterMetaDataBuilder.getBuilder(param).key(key).type(type).build());
            }
            if(parameterHasDtoObject(param)){

                Class<?> paramType = param.getType();

                for(Field field : paramType.getDeclaredFields()){
                    if(fieldHasJsonProperty(field)){
                        String key = getAnnotationValueFromField(field);
                        Class<?> type = field.getType();

                        parameterMetaData.add(ParameterMetaDataBuilder.getBuilder(field).key(key).type(type).build());
                    }
                }
            }
        }

        return parameterMetaData;
    }

    private String getAnnotationValueFromField(Field field) {
        for(Class<? extends Annotation> clazz  : INTERESTING_ANNOTATIONS.keySet()){
            if(field.isAnnotationPresent(clazz)){
                return INTERESTING_ANNOTATIONS.get(clazz).apply(field);
            }
        }
        //impossible
        return null;
    }

    private boolean fieldHasJsonProperty(Field field) {

        return field.isAnnotationPresent(JsonProperty.class);
    }

    private boolean parameterHasDtoObject(Parameter param) {

        Class<?> paramType = param.getType();

        List<Field> fields = Arrays.asList(paramType.getDeclaredFields());

        return fields.stream().anyMatch(field -> field.isAnnotationPresent(JsonProperty.class));
    }

    private String getAnnotationValueFromParameter(Parameter param) {

        for(Class<? extends Annotation> clazz  : INTERESTING_ANNOTATIONS.keySet()){
            if(param.isAnnotationPresent(clazz)){
                return INTERESTING_ANNOTATIONS.get(clazz).apply(param);
            }
        }
        //impossible
        return null;
    }

    private boolean parameterHasInterestingAnnotation(Parameter param) {
        for(Class<? extends Annotation> clazz  : INTERESTING_ANNOTATIONS.keySet()){
            if(param.isAnnotationPresent(clazz)){
                return true;
            }
        }
        return false;
    }

    private String getHttpMethod(Method method){
        for(Class<? extends Annotation> clazz : METHOD_TO_TEXT_MAP.keySet()){
            if(method.isAnnotationPresent(clazz)){
                return METHOD_TO_TEXT_MAP.get(clazz);
            }
        }
        return "";
    }

    private boolean methodHasHttpMethod(Method method) {
        for(Class<? extends Annotation> clazz : METHOD_TO_TEXT_MAP.keySet()){
            if(method.isAnnotationPresent(clazz)){
                return true;
            }
        }
        return false;
    }
}
