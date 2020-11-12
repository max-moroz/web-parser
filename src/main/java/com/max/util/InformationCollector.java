package com.max.util;

import com.max.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformationCollector {

    private Parser parser;

    private List<List<String>> electronicList = new ArrayList<>();
    private List<String> links = new ArrayList<>();


    public InformationCollector(Parser parser) {
        this.parser = parser;
    }

    public List<List<String>> getElectronicList() {
        return electronicList;
    }

    public void collectLinks(String category) throws IOException {
        for (int i = 1; i < 100; i++) {
            parser.parseCategoryPage(category, i);
            fillLinks();

            if (isLinksFull()) {
                saveInfo();
                break;
            }
        }
    }

    private void saveInfo() throws IOException {
        for (String link : links) {
            parser.parseItemPage(link);

            int numberOfBlocks = parser.getNumberOfItemCharacteristicsBlocks();
            List<String> characteristics = parser.getItemCharacteristics(numberOfBlocks);

            List<String> newList = new ArrayList<>();

            newList.add(parser.getItemName());
            newList.add(parser.getItemOldPrice());
            newList.add(parser.getItemNewPrice());
            newList.addAll(characteristics);
            newList.add(link);

            electronicList.add(newList);
        }
    }

    private void fillLinks() {
        int size = 100;

        if (links.isEmpty()) {
            links = parser.getItemLink();
        } else if (links.size() < size) {
            int availableSize = size - links.size();

            for (int i = 0; i < availableSize; i++) {
                links.add(parser.getItemLink().get(i));
            }
        }
    }

    private boolean isLinksFull() {
        return links.size() == 100;
    }

    public void cleanLinksList() {
        links.clear();
    }
}
