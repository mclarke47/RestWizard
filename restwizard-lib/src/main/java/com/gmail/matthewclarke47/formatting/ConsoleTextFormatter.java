package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import java.util.List;

public class ConsoleTextFormatter implements DocsFormatter{

    @Override
    public void print(List<ResourceMetaData> resourceMetaData) {

        resourceMetaData.stream().forEach(System.out::println);
    }
}
