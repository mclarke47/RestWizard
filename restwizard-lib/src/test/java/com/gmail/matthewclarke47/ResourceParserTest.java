package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.metadata.*;
import com.gmail.matthewclarke47.parsing.ResourceParser;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class ResourceParserTest {

    //TODO more non-happy path scenarios

    @Test
    public void shouldDocumentGetResource() {

        GetResource simpleGet = new GetResource();

        ResourceMetaData resourceMetaData = assertAndReturnResourceData(new ResourceParser(simpleGet).getMetaData());

        assertThat(resourceMetaData.getPath(), is(simpleGet.classPathValue));

        //TODO get rid of this get(0)
        MethodMetaData methodData = resourceMetaData.getMethodMetaDataList().get(0);

        assertMethod(methodData, "GET", simpleGet.methodPathValue);

        assertThat(methodData.getParameterMetaData().isEmpty(), is(true));
    }

    @Test
    public void shouldDocumentPutResource() {

        PutResource simplePut = new PutResource();

        ResourceMetaData resourceMetaData = assertAndReturnResourceData(new ResourceParser(simplePut).getMetaData());

        assertThat(resourceMetaData.getPath(), is(simplePut.classPathValue));

        //TODO get rid of this get(0)
        MethodMetaData methodData = resourceMetaData.getMethodMetaDataList().get(0);

        assertMethod(methodData, "PUT", simplePut.methodPathValue);

        assertThat(methodData.getParameterMetaData(), containsInAnyOrder(new PathParameterMetaData("id", String.class)));
    }

    @Test
    public void shouldDocumentPostResource() {

        //TODO simplify this
        PostResource simplePost = new PostResource();

        ResourceMetaData resourceMetaData = assertAndReturnResourceData(new ResourceParser(simplePost).getMetaData());

        assertThat(resourceMetaData.getPath(), is(simplePost.classPathValue));

        List<ParameterMetaData> paramList1 = Lists.newArrayList(new QueryParameterMetaData("id", String.class));

        List<ParameterMetaData> paramList2 = Lists.newArrayList(
                propertyParam("thisThing", String.class),
                propertyParam("otherParam", String.class),
                propertyParam("count", int.class),
                propertyParam("isBoolean", boolean.class));

        assertThat(resourceMetaData.getMethodMetaDataList(),
                containsInAnyOrder(
                        methodData("POST", "some/endpoint/postObject", paramList2),
                        methodData("POST", "some/endpoint", paramList1)));
    }

    private MethodMetaData methodData(String method, String path, List<ParameterMetaData> list) {
        return new MethodMetaData(method, path, list);
    }

    private PropertyParameterMetaData propertyParam(String key, Class<?> type) {
        return new PropertyParameterMetaData(key, type);
    }

    private ResourceMetaData assertAndReturnResourceData(Optional<ResourceMetaData> resourceMetaDataOptional) {
        assertThat(resourceMetaDataOptional.isPresent(), is(true));

        return resourceMetaDataOptional.get();
    }

    private void assertMethod(MethodMetaData methodData, String httpMethodName, String methodPath) {
        assertThat(methodData.getHttpMethod(), is(httpMethodName));
        assertThat(methodData.getPathSuffix(), is(methodPath));
    }
}