package com.max.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private static final String URL = "https://allegro.pl/kategoria/";
    private static final String BARGAIN_ZONE = "&string=bargain_zone&p=";

    private Document document;
    private Document itemDocument;

    public void parseCategoryPage(String category, int pageNumber) throws IOException {
        this.document = Jsoup.connect(URL + category + BARGAIN_ZONE + pageNumber)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(600000)
                .maxBodySize(0)
                .get();
    }

    public Elements getCategoryDiscountItems() {
        Elements listOfDiscountItems = getItemsContainers();
        return listOfDiscountItems.select("span:contains(%)");
    }

    private Elements getItemsContainers() {
        Elements generalItemsContainer = document.select("div[data-box-name=items-v3]");
        return generalItemsContainer.select("div[style=display:contents]");
    }

    public List<String> getItemLink() {
        Elements discountItems = getCategoryDiscountItems();
        return discountItems.parents().prev().select("h2 > a").stream()
                .map(e -> {
                            Element a = e.select("a").first();
                            return a.attr("abs:href");
                        }
                ).collect(Collectors.toList());
    }


    public void parseItemPage(String url) throws IOException {
        this.itemDocument = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(600000)
                .maxBodySize(0)
                .get();
    }

    public String getItemName() {
        return itemDocument.select("h1[class$=_9a071_1S3No]").text();
    }

    public String getItemOldPrice() {
        return itemDocument.select("s[class$=_9a071_3Xr_c]").text();
    }

    public String getItemNewPrice() {
        return itemDocument.select("div[class$=_9a071_2MEB_]").text();
    }

    public int getNumberOfItemCharacteristicsBlocks() {
        return itemDocument.select("div[data-box-name=Parameters] li._1bmp9").size();
    }

    public Elements getCharacteristicsBlock(int blockNumber) {
        return itemDocument.select("div[data-box-name=Parameters] li._1bmp9:lt(" + blockNumber + ")");
    }

    public List<String> getItemCharacteristics(int blockNumber) {
        Elements characteristics = getCharacteristicsBlock(blockNumber);
        return characteristics.stream().map(Element::text).collect(Collectors.toList());
    }
}
