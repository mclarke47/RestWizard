package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.metadata.ResourceMetaData;
import com.gmail.matthewclarke47.parsing.ResourceParser;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import org.eclipse.jetty.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestWizard implements ServerLifecycleListener {
    private JerseyEnvironment jersey;
    private static List<ResourceMetaData> list = new ArrayList<>();

    public RestWizard(JerseyEnvironment jersey) {
        this.jersey = jersey;
    }

    @Override
    public void serverStarted(Server server) {

        List<ResourceMetaData> list = jersey.getResourceConfig().getSingletons().stream()
                .map(obj -> new ResourceParser(obj)
                        .getMetaData())
                .filter(Optional::isPresent)
                .<ResourceMetaData>map(Optional::get)
                .collect(Collectors.toList());

        RestWizard.list.addAll(list);

        //docsFormatter.print(list);


    }

    public DocResource getDocResource(){
        return new DocResource(list);
    }

}

