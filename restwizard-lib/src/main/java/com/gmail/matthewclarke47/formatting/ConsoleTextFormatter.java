package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.ResourceMetaData;

public class ConsoleTextFormatter implements DocsFormatter{

    @Override
    public void print(ResourceMetaData resourceMetaData) {
        System.out.println(resourceMetaData);

    }
}
