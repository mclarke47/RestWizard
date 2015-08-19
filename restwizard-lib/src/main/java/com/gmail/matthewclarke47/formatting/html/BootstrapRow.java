package com.gmail.matthewclarke47.formatting.html;

import com.google.common.collect.Lists;

import java.util.List;

public class BootstrapRow {

    private String title;
    private final List<String> cols = Lists.newArrayList();

    public BootstrapRow(String title) {

        this.title = title;
    }

    public BootstrapRow addCol(String str) {
        cols.add(str);
        return this;
    }

    @Override
    public String toString() {
        String str = "<div class=\"row \">";
        str += "<h2>"+title+"</h2>";
        int individual = 12 / cols.size()  ;

        for (String col : cols) {
            str += "<div class=\"col-sm-"+individual+"\">";
            str += col;
            str += "</div>";
        }
        str += "</div>";
        return str;
    }
}
