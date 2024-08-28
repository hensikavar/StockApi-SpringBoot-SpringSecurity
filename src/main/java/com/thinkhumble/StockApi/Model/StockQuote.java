package com.thinkhumble.StockApi.Model;

/**
 * Model class representing a stock quote.
 */
public class StockQuote {

    private String symbol;
    private double price;
    private double change;
    private double percentageChange;
    private String timestamp;
    private double openPrice;         // Opening price of the stock
    private double highPrice;         // Highest price during the trading day
    private double lowPrice;          // Lowest price during the trading day
    private long volume;              // Trading volume of the stock
    private double previousClose;     // Previous day's closing price

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

}

