package com.gmail.matthewclarke47;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("putResourcePath/")
public class PutResource {

    @PUT()
    @Path("some/person")
    public Response postPerson(@JsonProperty("name") String name) {
        return Response.ok().build();
    }

    @PUT()
    @Path("some/endpoint/{id}")
    public Response postMethod(@PathParam("id") String idParam) {
        return Response.ok().build();
    }
}