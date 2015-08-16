package com.gmail.matthewclarke47;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ResourceParserTest {

    @Test
    public void shouldDocumentGetResource(){

        GetResources.SimpleGet simpleGet = GetResources.simpleGet();

        ResourceMetaData resourceMetaData = assertAndReturnResourceData(new ResourceParser(simpleGet).getMetaData());

        assertThat(resourceMetaData.getPath(), is(simpleGet.classPathValue));

        MethodMetaData methodData = resourceMetaData.getMethodMetaDataList().get(0);

        assertMethod(methodData, "GET", simpleGet.methodPathValue);

        ParameterMetaData parameterMetaData = methodData.getParameterMetaData().get(0);

        assertThat(parameterMetaData, instanceOf(PropertyParameterMetaData.class));
        assertThat(parameterMetaData.getKey(), is(simpleGet.jsonPropertyValue));
        assertThat(parameterMetaData.getType(), isA(Class.class));
    }

    private ResourceMetaData assertAndReturnResourceData(Optional<ResourceMetaData> resourceMetaDataOptional){
        assertThat(resourceMetaDataOptional.isPresent(), is(true));

        return resourceMetaDataOptional.get();
    }

    private void assertMethod(MethodMetaData methodData, String httpMethodName, String methodPath){
        assertThat(methodData.getHttpMethod(), is(httpMethodName));
        assertThat(methodData.getPathSuffix(), is(methodPath));
    }
}