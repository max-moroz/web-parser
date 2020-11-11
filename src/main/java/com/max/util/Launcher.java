package com.max.util;

import com.max.parser.Parser;

import java.io.IOException;

public class Launcher {

    private static final String ELECTRONIC = "elektronika";

    private Parser parser;
    private InformationCollector collector;
    private Saver saver;

    public Launcher(Parser parser, InformationCollector collector, Saver saver) {
        this.parser = parser;
        this.collector = collector;
        this.saver = saver;
    }

    public void launchParsingAndSaving() throws IOException {

        for (int i = 1; i <= 100; i++) {
            parser.savePageIntoDocument(ELECTRONIC, i);

            collector.fillCharacteristicsList();

            //todo - limit to 100 entries
            collector.saveElectronicItems();

            if (collector.checkNumberOfItems() >= 100) {
                break;
            }
        }

        saver.saveItemsToFile();
    }
}
