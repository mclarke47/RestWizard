package com.gmail.matthewclarke47.view;

import com.gmail.matthewclarke47.metadata.ResourceMetaData;
import io.dropwizard.views.View;

import java.util.List;

public class DocView extends View {
    private List<ResourceMetaData> resources;

    protected DocView(String templateName) {
        super(templateName);
    }

    public DocView(String templateName, List<ResourceMetaData> resources) {
        super(templateName);
        this.resources = resources;
    }

    public List<ResourceMetaData> getResources() {
        return resources;
    }

    public void setResources(List<ResourceMetaData> resources) {
        this.resources = resources;
    }
}
