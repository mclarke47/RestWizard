package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class TestResources {


}

class PostObject {

    @JsonProperty("thisThing")
    private String thingParam;

    @JsonProperty("otherParam")
    private String otherParam;

    @JsonProperty("isBoolean")
    private boolean isBoolean;

    @JsonProperty("count")
    private int count;

    public String getThingParam() {
        return thingParam;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public int getCount() {
        return count;
    }
}

@Path("path/")
class PostResource {

    public String classPathValue = "path/";
    public String methodPathValue = "some/endpoint";
    public String methodPathValuePostObject = "some/endpoint/postObject";

    @POST()
    @Path("some/endpoint")
    public Response postMethod(@QueryParam("id") String id) {
        return Response.ok().build();
    }

    @POST()
    @Path("some/endpoint/postObject")
    public Response postMethod(PostObject postObject) {
        return Response.ok().build();
    }
}

@Path("path/")
class GetResource {

    public String classPathValue = "path/";
    public String methodPathValue = "some/endpoint";

    @GET()
    @Path("some/endpoint")
    public Response postMethod() {
        return Response.ok().build();
    }
}

@Path("path/")
class PutResource {

    public String classPathValue = "path/";
    public String methodPathValue = "some/endpoint/{id}";
    public String pathParamValue = "id";

    @PUT()
    @Path("some/endpoint/{id}")
    public Response postMethod(@PathParam("id") String idParam) {
        return Response.ok().build();
    }
}
