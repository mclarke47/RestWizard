package com.gmail.matthewclarke47;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public
@Path("getResourcePath/")
class GetResource {

    @GET()
    @Path("some/endpoint")
    @Produces(MediaType.APPLICATION_JSON)
    public Person postMethod() {
        return new Person("a person", 54);
    }
}
