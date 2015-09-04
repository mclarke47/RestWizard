package com.gmail.matthewclarke47;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public @Path("getResourcePath/")
class GetResource {

    @GET()
    @Path("some/endpoint")
    public Response postMethod(){
        return Response.ok().build();
    }
}
