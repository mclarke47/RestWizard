package com.gmail.matthewclarke47.parsing;

import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Optional;

public class ResourceParser {

    private Object obj;
    private Optional<ResourceMetaData> metaData;

    public ResourceParser(Object obj) {
        this.obj = obj;
        this.metaData = document(obj);
    }

    private Optional<ResourceMetaData> document(Object obj) {

        if (isResource()) {
            Class<?> clazz = obj.getClass();

            List<MethodMetaData> methodMetaData = new ClassParser(clazz).getMethodMetaDataList();

            return Optional.of(new ResourceMetaData(clazz.getAnnotation(Path.class).value(), methodMetaData));
        }
        return Optional.empty();
    }

    private boolean isResource() {
        return obj.getClass().isAnnotationPresent(Path.class);
    }

    public Optional<ResourceMetaData> getMetaData() {
        return metaData;
    }
}