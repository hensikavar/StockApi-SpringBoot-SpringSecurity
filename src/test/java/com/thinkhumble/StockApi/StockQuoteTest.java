package com.thinkhumble.StockApi;

import com.thinkhumble.StockApi.Model.StockQuote;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockQuoteTest {

    @Test
    public void testStockQuoteGettersAndSetters() {
        StockQuote stockQuote = new StockQuote();
        stockQuote.setSymbol("AAPL");
        stockQuote.setPrice(150.00);
        stockQuote.setChange(1.23);
        stockQuote.setPercentageChange(0.82);
        stockQuote.setTimestamp("2024-08-27");
        stockQuote.setOpenPrice(148.00);
        stockQuote.setHighPrice(152.00);
        stockQuote.setLowPrice(147.00);
        stockQuote.setVolume(1000000L);
        stockQuote.setPreviousClose(149.00);

        assertEquals("AAPL", stockQuote.getSymbol());
        assertEquals(150.00, stockQuote.getPrice());
        assertEquals(1.23, stockQuote.getChange());
        assertEquals(0.82, stockQuote.getPercentageChange());
        assertEquals("2024-08-27", stockQuote.getTimestamp());
        assertEquals(148.00, stockQuote.getOpenPrice());
        assertEquals(152.00, stockQuote.getHighPrice());
        assertEquals(147.00, stockQuote.getLowPrice());
        assertEquals(1000000L, stockQuote.getVolume());
        assertEquals(149.00, stockQuote.getPreviousClose());
    }
}
