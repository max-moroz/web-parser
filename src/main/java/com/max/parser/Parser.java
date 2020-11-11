package com.max.parser;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private static final String URL = "https://allegro.pl/kategoria/";
    private static final String BARGAIN_ZONE = "?string=bargain_zone&p=";
    private static final String SUFFIX = "&bmatch=cl-dict55-ctx-ele-1-4-1106";

    private static final String DISCOUNT = "%";

    private static final String WEB_PAGE = "src/main/resources/webpage.html";


//    public Document parseWebPage(/*String category*/) throws IOException {
//        return Jsoup.connect(URL + ELEKTRONIKA + BARGAIN_ZONE)
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
//                .referrer("http://www.google.com")
//                .timeout(600000)
//                .maxBodySize(0)
//                .get();
//    }


    public Document parseWebPage() throws IOException {
        File file = new File(WEB_PAGE);
        return Jsoup.parse(file, null);
    }

/*    public Document parseWebPage(String category, int pageNumber) throws IOException {
                return Jsoup.connect(URL + category + BARGAIN_ZONE + pageNumber + SUFFIX)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(600000)
                .maxBodySize(0)
                .get();
    }*/

    public void savePageIntoDocument(String category, int pageNumber) throws IOException {
        System.out.println(URL + category + BARGAIN_ZONE + pageNumber + SUFFIX);
        Connection.Response response = Jsoup.connect(URL + category + BARGAIN_ZONE + pageNumber).execute();
        Document document = response.parse();

        File htmlFile = new File(WEB_PAGE);
        if (htmlFile.exists()) {
            RandomAccessFile raf = new RandomAccessFile(htmlFile, "rw");
            raf.setLength(0);
        }
        FileUtils.writeStringToFile(htmlFile, document.outerHtml(), StandardCharsets.UTF_8);
    }

    private Elements getItemsContainers() throws IOException {
        Elements generalItemsContainer = parseWebPage().select("div[data-box-name=items-v3]");
        return generalItemsContainer.select("div[style=display:contents]");
    }

    public Elements getCategoryDiscountItems() throws IOException {
        Elements listOfDiscountItems = getItemsContainers();
        return listOfDiscountItems.select("span:contains(" + DISCOUNT + ")");
    }

  /*  public List<String> getListOfCategoryDiscountItems() throws IOException {
        Elements listOfDiscountItems = getItemsContainers();
        return listOfDiscountItems.select("span:contains(" + DISCOUNT + ")").stream().map(Element::text).collect(Collectors.toList());
    }*/

    public List<String> getItemOldPrice() throws IOException {
        Elements discountItems = getCategoryDiscountItems();
        return discountItems.next().stream().map(Element::text).collect(Collectors.toList());
    }

    //TODO - price
    public List<String> getItemNewPrice() throws IOException {
        Elements discountItems = getCategoryDiscountItems();

//        return discountItems.parents().next().stream().map(e -> {
//            StringBuilder price = new StringBuilder();
//            return price.append(e.select(/*"span[class$=_lf05o]"*/ "div[class$=_9c44d_2K6FN]").text())
//                    .append(System.lineSeparator()).toString();
//        }).
//                collect(Collectors.toList());
        Elements newPrices = discountItems.parents().next().select("div[class$=_9c44d_2K6FN]");
        return newPrices.stream().map(Element::text).collect(Collectors.toList());
    }


    public List<String> getItemLink() throws IOException {
        Elements discountItems = getCategoryDiscountItems();
        return discountItems.parents().prev().select("h2 > a").stream()
                .map(e -> {
                            Element a = e.select("a").first();
                            return a.attr("abs:href");
                        }
                ).collect(Collectors.toList());
    }

    public List<String> getItemName() throws IOException {
        Elements discountItems = getCategoryDiscountItems();
        return discountItems.parents().prev().select("h2 > a").stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }
}
