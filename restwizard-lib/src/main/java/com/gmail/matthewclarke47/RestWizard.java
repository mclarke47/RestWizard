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
    private static List<ResourceMetaData> list = new ArrayList<>();
    private JerseyEnvironment jersey;

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

        // FIXME: 10/09/2015 static assignment is not nice!
        RestWizard.list.addAll(list);

    }

    public DocResource getDocResource() {
        return new DocResource(list);
    }

}

