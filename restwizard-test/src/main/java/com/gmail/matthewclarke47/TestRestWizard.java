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

@Path("path/")
class PostResource {

    @POST()
    @Path("some/endpoint")
    public Response postMethod(@QueryParam("id") String id){
        return Response.ok().build();
    }
}

@Path("path/")
class GetResource {

    @GET()
    @Path("some/endpoint")
    public Response postMethod(@JsonProperty("thisThing") String thingParam){
        return Response.ok().build();
    }
}

@Path("path/")
class PutResource {

    @PUT()
    @Path("some/endpoint/{id}")
    public Response postMethod(@PathParam("id") String idParam){
        return Response.ok().build();
    }
}
class HelloWorldConfiguration extends Configuration {

}
