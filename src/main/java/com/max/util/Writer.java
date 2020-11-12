package com.max.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    public static final String SEPARATOR = "  |  ";
    public static final String LINE_SEPARATOR = System.lineSeparator();

    private InformationCollector collector;

    public Writer(InformationCollector collector) {
        this.collector = collector;
    }

    public void saveItemsToFile() throws IOException {
        FileWriter csvWriter = new FileWriter("src/main/resources/items.csv");
        csvWriter.append("Name");
        csvWriter.append(SEPARATOR);
        csvWriter.append("Old Price");
        csvWriter.append(SEPARATOR);
        csvWriter.append("New Price");
        csvWriter.append(SEPARATOR);
        csvWriter.append("Characteristics");
        csvWriter.append(SEPARATOR);
        csvWriter.append("Link");
        csvWriter.append(LINE_SEPARATOR);

        List<List<String>> data = collector.getElectronicList();

        for (List<String> rowData : data) {
            csvWriter.append(String.join(SEPARATOR, rowData));
            csvWriter.append(LINE_SEPARATOR);
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
