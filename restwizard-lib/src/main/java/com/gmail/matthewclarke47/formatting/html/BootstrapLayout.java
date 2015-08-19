package com.gmail.matthewclarke47.formatting.html;

import com.google.common.collect.Lists;

import java.util.List;

public class BootstrapLayout {

    private final List<BootstrapRow> rows = Lists.newArrayList();

    @Override
    public String toString() {
        String str = "";

        for (BootstrapRow row : rows){
            str += row;
        }
        return str;
    }

    public BootstrapRow row(String title) {
        BootstrapRow row = new BootstrapRow(title);
        rows.add(row);
        return row;
    }

    public BootstrapRow row() {
        return row("");
    }
}
