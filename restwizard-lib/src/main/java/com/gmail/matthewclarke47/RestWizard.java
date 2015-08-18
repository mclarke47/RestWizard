package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.formatting.DocsFormatter;
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
    private DocsFormatter docsFormatter;

    private List<Optional<ResourceMetaData>> resourceMetaDataList = new ArrayList<>();

    public RestWizard(JerseyEnvironment jersey, DocsFormatter docsFormatter) {
        this.jersey = jersey;
        this.docsFormatter = docsFormatter;
    }

    public void serverStarted(Server server) {
        for (Object obj : jersey.getResourceConfig().getSingletons()) {

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

