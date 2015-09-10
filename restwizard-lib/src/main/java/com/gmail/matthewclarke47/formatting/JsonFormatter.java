package com.gmail.matthewclarke47.formatting;

import com.gmail.matthewclarke47.metadata.PropertyParameterMetaData;

import java.util.List;

public class JsonFormatter {
    private List<PropertyParameterMetaData> propertyParams;

    public JsonFormatter(List<PropertyParameterMetaData> propertyParams) {

        this.propertyParams = propertyParams;
    }

    public String format() {

        if (propertyParams.isEmpty()) {
            return "";
        }

        String str = "{\n\t";

        for (PropertyParameterMetaData property : propertyParams) {
            if (!str.equals("{\n\t")) {
                str += ",\n\t";
            }
            str += "\"" + property.getKey() + "\": " + TypeInferringExampleGenerator.getExample(property.getType());
        }
        str += "\n}";
        return str;
    }
}
