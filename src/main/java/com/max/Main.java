package com.max;

import com.max.context.Context;
import com.max.parser.Parser;
import com.max.util.InformationCollector;
import com.max.util.Launcher;
import com.max.util.Writer;

public class Main {

    public static void main(String[] args) throws Exception {
        Context context = new Context();

        Parser parser = context.getBean(Parser.class);
        InformationCollector collector = new InformationCollector(parser);
        Writer writer = new Writer(collector);
        Launcher launcher = new Launcher(collector, writer);

        launcher.launchParsingAndSaving();
    }
}
