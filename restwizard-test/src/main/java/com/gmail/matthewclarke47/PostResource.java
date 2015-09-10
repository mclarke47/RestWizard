package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("postResourcePath/")
public class PostResource {

    @POST()
    @Path("some/person")
    public Response postMethod(@QueryParam("id") String id, @JsonProperty("name") String name) {
        return Response.ok().build();
    }

    @POST()
    @Path("some/endpoint/postObject")
    public Response postMethod(PostObject postObject) {
        return Response.ok().build();
    }
}