package com.gmail.matthewclarke47.formatting;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

public class ClassToJsonFormatter {
    private Class<?> returnType;

    public ClassToJsonFormatter(Class<?> returnType) {

        this.returnType = returnType;
    }

    public String format() {

        if(returnType.equals(Response.class)){
            return "";
        }

        String str = "{\n\t";

        for (Field field : returnType.getDeclaredFields()) {
            if (!str.equals("{\n\t")) {
                str += ",\n\t";
            }
            str += "\"" + field.getName() + "\": " + TypeInferringExampleGenerator.getExample(field.getType());
        }
        str += "\n}";
        return str;
    }
}
