package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.matthewclarke47.formatting.ConsoleTextFormatter;
import com.gmail.matthewclarke47.formatting.HtmlFormatter;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class TestRestWizard extends Application<HelloWorldConfiguration> {

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new PostResource());
        environment.jersey().register(new GetResource());
        environment.jersey().register(new PutResource());
        environment.lifecycle().addServerLifecycleListener(new RestWizard(environment.jersey(), new HtmlFormatter()));
    }

    @Override
    public void initialize(Bootstrap bootstrap) {

    }

    public static void main(String... args) throws Exception {
        new TestRestWizard().run(args);
    }
}

class PostObject{

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

@Path("postResourcePath/")
class PostResource {

    @POST()
    @Path("some/person")
    public Response postMethod(@QueryParam("id") String id, @JsonProperty("name") String name){
        return Response.ok().build();
    }

    @POST()
    @Path("some/endpoint/postObject")
    public Response postMethod(PostObject postObject){
        return Response.ok().build();
    }
}

@Path("getResourcePath/")
class GetResource {

    @GET()
    @Path("some/endpoint")
    public Response postMethod(){
        return Response.ok().build();
    }
}

@Path("putResourcePath/")
class PutResource {

    @PUT()
    @Path("some/person")
    public Response postPerson(@JsonProperty("name") String name){
        return Response.ok().build();
    }

    @PUT()
    @Path("some/endpoint/{id}")
    public Response postMethod(@PathParam("id") String idParam){
        return Response.ok().build();
    }
}
class HelloWorldConfiguration extends Configuration {

}
