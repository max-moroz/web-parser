package com.max.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Saver {

    private InformationCollector collector;

    public Saver(InformationCollector collector) {
        this.collector = collector;
    }

    public void saveItemsToFile() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/items.csv"));

        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer)
                    .withSeparator('*')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(collector.getElectronicList());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
