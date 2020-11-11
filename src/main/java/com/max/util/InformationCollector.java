package com.max.util;

import com.max.entity.Electronic;
import com.max.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformationCollector {

    private static final String ELECTRONIC = "elektronika";

    private Parser parser;

    private List<Electronic> electronicList = new ArrayList<>();

    private List<String> names = new ArrayList<>();
    private List<String> oldPrices = new ArrayList<>();
    private List<String> newPrices = new ArrayList<>();
    private List<String> links = new ArrayList<>();


    public List<Electronic> getElectronicList() {
        return electronicList;
    }

    public InformationCollector(Parser parser) {
        this.parser = parser;
    }


    public void saveElectronicItems() throws IOException {
        //parser.savePageIntoDocument(ELECTRONIC, 1);
       // fillCharacteristicsList();
        int numberOfItems = parser.getCategoryDiscountItems().size();

        for (int i = 0; i < numberOfItems; i++) {
            parser.parseWebPage();
            Electronic electronic = new Electronic();

            electronic.setName(names.get(i));
            electronic.setOldPrice(oldPrices.get(i));
            electronic.setCurrentPrice(newPrices.get(i));
            electronic.setLink(links.get(i));

            electronicList.add(electronic);

            if(electronicList.size() == 100){
                break;
            }
        }
    }

    public void fillCharacteristicsList() throws IOException {
        int size = 100;

        if (names.isEmpty()) {
            names = parser.getItemName();
            oldPrices = parser.getItemOldPrice();
            newPrices = parser.getItemNewPrice();
            links = parser.getItemLink();
        } else if (checkNumberOfItems() <= size) {
            int availableSize = size - checkNumberOfItems();

            for (int i = 0; i < availableSize; i++) {
                names.add(parser.getItemName().get(i));
                oldPrices.add(parser.getItemOldPrice().get(i));
                newPrices.add(parser.getItemNewPrice().get(i));
                links.add(parser.getItemLink().get(i));
            }
        }
    }

    public int checkNumberOfItems() {
        return names.size();
    }
}
