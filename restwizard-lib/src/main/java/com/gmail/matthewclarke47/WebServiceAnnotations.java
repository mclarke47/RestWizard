package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.util.function.Function;

public class WebServiceAnnotations {

    public static final Map<Class<? extends Annotation>, String> HTTP_METHOD_TO_TEXT =
            ImmutableMap.<Class<? extends Annotation>, String>builder()
                    .put(GET.class, HttpMethod.GET)
                    .put(POST.class, HttpMethod.POST)
                    .put(PUT.class, HttpMethod.PUT)
                    .put(DELETE.class, HttpMethod.DELETE).build();

    public static final Map<Class<? extends Annotation>, Function<AnnotatedElement, String>> INTERESTING_ANNOTATIONS_TO_VALUE =
            ImmutableMap.<Class<? extends Annotation>, Function<AnnotatedElement, String>>builder()
                    .put(JsonProperty.class, element -> element.getAnnotation(JsonProperty.class).value())
                    .put(QueryParam.class, element -> element.getAnnotation(QueryParam.class).value())
                    .put(PathParam.class, element -> element.getAnnotation(PathParam.class).value())
                    .build();

    private WebServiceAnnotations() {
    }
}
