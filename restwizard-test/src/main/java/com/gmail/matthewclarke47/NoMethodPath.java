package com.gmail.matthewclarke47;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("avast/")
public class NoMethodPath {


    @GET
    public Response postMethod() {
        return Response.ok().build();
    }
}
