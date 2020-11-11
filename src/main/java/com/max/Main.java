package com.max;

import com.max.context.Context;
import com.max.parser.Parser;
import com.max.util.InformationCollector;
import com.max.util.Launcher;
import com.max.util.Saver;

public class Main {

    //private static final String ELEKTRONIKA = "elektronika";

    public static void main(String[] args) throws Exception {
        Context context = new Context();

        Parser parser = context.getBean(Parser.class);
        InformationCollector collector = new InformationCollector(parser);
        Saver saver = new Saver(collector);
        Launcher launcher = new Launcher(parser, collector, saver);


       // collector.saveElectronicItems();
        launcher.launchParsingAndSaving();


        //System.out.println(parser.getCategoryDiscountItems(ELEKTRONIKA));
        // System.out.println(parser.getListOfCategoryDiscountItems(ELEKTRONIKA));
        //System.out.println(parser.getItemOldPrice(ELEKTRONIKA));
//        System.out.println(parser.getItemLink(ELEKTRONIKA));
//        System.out.println(parser.getItemName(ELEKTRONIKA));
//        System.out.println(parser.getItemNewPrice(ELEKTRONIKA));

    }
}
