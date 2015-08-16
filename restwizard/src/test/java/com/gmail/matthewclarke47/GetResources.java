package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class GetResources {

    private static GetResources INSTANCE = new GetResources();

    public static SimpleGet simpleGet(){
        return INSTANCE.newSimpleGet();
    }

    private SimpleGet newSimpleGet(){
        return new SimpleGet();
    }


    @Path("path/")
    public class SimpleGet {

        @GET()
        @Path("some/endpoint")
        public Response postMethod(@JsonProperty("thisThing") String thingParam){
            return Response.ok().build();
        }

        public String classPathValue = "path/";
        public String methodPathValue = "some/endpoint";
        public String jsonPropertyValue = "thisThing";
    }
}
