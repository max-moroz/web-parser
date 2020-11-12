package com.max.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Launcher {

    private final List<String> categories = Arrays.asList("elektronika?bmatch=cl-dict55-ctx-ele-1-4-1106", "zdrowie?bmatch=dict55-ctx-hea-1-4-1106", "dom-i-ogrod?bmatch=dict55-ctx-hou-1-4-1106");

    private InformationCollector collector;
    private Writer writer;

    public Launcher(InformationCollector collector, Writer writer) {
        this.collector = collector;
        this.writer = writer;
    }

    public void launchParsingAndSaving() throws IOException {
        for (String category : categories) {
            collector.collectLinks(category);
            writer.saveItemsToFile();
            collector.cleanLinksList();
        }
    }
}
