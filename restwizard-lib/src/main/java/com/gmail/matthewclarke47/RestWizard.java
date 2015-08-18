package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.formatting.DocsFormatter;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;
import com.gmail.matthewclarke47.parsing.ResourceParser;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import org.eclipse.jetty.server.Server;

import java.util.Optional;
import java.util.stream.Collectors;

public class RestWizard implements ServerLifecycleListener {
    private JerseyEnvironment jersey;
    private DocsFormatter docsFormatter;

    public RestWizard(JerseyEnvironment jersey, DocsFormatter docsFormatter) {
        this.jersey = jersey;
        this.docsFormatter = docsFormatter;
    }

    @Override
    public void serverStarted(Server server) {

        docsFormatter.print(jersey.getResourceConfig().getSingletons().stream()
                .map(obj -> new ResourceParser(obj)
                        .getMetaData())
                .filter(Optional::isPresent)
                .<ResourceMetaData>map(Optional::get)
                .collect(Collectors.toList()));
    }
}

