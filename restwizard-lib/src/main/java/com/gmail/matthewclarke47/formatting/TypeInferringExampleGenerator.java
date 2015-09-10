package com.gmail.matthewclarke47.formatting;

public class TypeInferringExampleGenerator {

    public static String getExample(Class<?> type) {

        if (type.equals(boolean.class)) {
            return "true";
        } else if (type.equals(int.class)) {
            return "12345";
        }
        return "\"ABC\"";
    }
}
