package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.formatting.html.DocsPage;
import com.gmail.matthewclarke47.metadata.MethodMetaData;
import com.gmail.matthewclarke47.metadata.ParameterMetaData;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import java.io.FileWriter;
import java.util.List;

public class HtmlFormatter implements DocsFormatter {
    @Override
    public void print(List<ResourceMetaData> resourceMetaData) {

        FileWriter fileWriter;

        DocsPage docsPage = new DocsPage("Rest Wizard test app");

        resourceMetaData.stream().forEach(docsPage::addResource);

        try {
            fileWriter = new FileWriter("./doc.html");
            fileWriter.append(docsPage.build());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            //Ignore for now
        }

    }


}
