package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.formatting.DocsFormatter;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Server;

import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RestWizard implements ServerLifecycleListener {
    private JerseyEnvironment jersey;
    private DocsFormatter docsFormatter;

    private List<Optional<ResourceMetaData>> resourceMetaDataList = new ArrayList<>();

    public RestWizard(JerseyEnvironment jersey, DocsFormatter docsFormatter) {
        this.jersey = jersey;
        this.docsFormatter = docsFormatter;
    }

    public void serverStarted(Server server) {
        for(Object obj : jersey.getResourceConfig().getSingletons()){

            ResourceParser resourceParser = new ResourceParser(obj);

                resourceMetaDataList.add(resourceParser.getMetaData());
        }

        final List<ResourceMetaData> someList = resourceMetaDataList
                .stream()
                .filter(Optional::isPresent)
                .<ResourceMetaData>map(Optional::get)
                .collect(Collectors.toList());

        docsFormatter.print(someList);
    }
}

@Path("docs")
class Docs {

    private Environment environment;

    Docs(Environment environment){
        this.environment = environment;
    }


    @GET()
    @Path("/docs")
    public Response get(){
        return Response.ok().entity(Entity.json(new DocsResult(environment.jersey().getResourceConfig().getEndpointsInfo()))).build();
    }

    class DocsResult {

        private String endpoints;

        public DocsResult(String endpoints) {
            this.endpoints = endpoints;
        }

        public String getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(String endpoints) {
            this.endpoints = endpoints;
        }
    }
}

