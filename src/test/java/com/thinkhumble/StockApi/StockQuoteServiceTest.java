package com.thinkhumble.StockApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkhumble.StockApi.Exception.StockApiException;
import com.thinkhumble.StockApi.Model.APIResponse;
import com.thinkhumble.StockApi.Model.StockQuote;
import com.thinkhumble.StockApi.Service.StockQuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockQuoteServiceTest {

    @InjectMocks
    private StockQuoteService stockQuoteService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the successful retrieval of a stock quote by symbol.
     * Verifies correct parsing and calculation of stock data.
     */
    @Test
    public void testGetQuoteBySymbol_Success() throws Exception {
        String symbol = "AAPL";
        String responseBody = "{ \"Time Series (Daily)\": { \"2024-08-26\": { \"1. open\": \"148.00\", \"2. high\": \"152.00\", \"3. low\": \"147.00\", \"4. close\": \"150.00\", \"5. volume\": \"1000000\" } }, \"Meta Data\": {} }";
        APIResponse apiResponse = new APIResponse();
        Map<String, Map<String, String>> timeSeriesDaily = new HashMap<>();
        Map<String, String> dailyData = new HashMap<>();
        dailyData.put("1. open", "148.00");
        dailyData.put("2. high", "152.00");
        dailyData.put("3. low", "147.00");
        dailyData.put("4. close", "150.00");
        dailyData.put("5. volume", "1000000");
        timeSeriesDaily.put("2024-08-26", dailyData);
        apiResponse.setTimeSeriesDaily(timeSeriesDaily);

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(responseBody);
        when(objectMapper.readValue(anyString(), eq(APIResponse.class))).thenReturn(apiResponse);

        StockQuote stockQuote = stockQuoteService.getQuoteBySymbol(symbol);

        assertNotNull(stockQuote);
        assertEquals("AAPL", stockQuote.getSymbol());
        assertEquals(150.00, stockQuote.getPrice());
        assertEquals(2.00, stockQuote.getChange());
        assertEquals(1.35, stockQuote.getPercentageChange());
        assertEquals("2024-08-26", stockQuote.getTimestamp());
        assertEquals(148.00, stockQuote.getOpenPrice());
        assertEquals(152.00, stockQuote.getHighPrice());
        assertEquals(147.00, stockQuote.getLowPrice());
        assertEquals(1000000, stockQuote.getVolume());
        assertEquals(150.00, stockQuote.getPreviousClose());
    }

    /**
     * Tests handling of API failures, such as HTTP 404 errors.
     * Verifies that StockApiException is thrown with an appropriate message.
     */
    @Test
    public void testGetQuoteBySymbol_ApiFailure() {
        String symbol = "AAPL";

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        StockApiException thrown = assertThrows(StockApiException.class, () -> stockQuoteService.getQuoteBySymbol(symbol));
        assertEquals("HTTP error occurred while fetching stock data: 404 Not Found", thrown.getMessage());
    }

    /**
     * Tests handling of malformed JSON data.
     * Verifies that StockApiException is thrown due to parsing issues.
     */
    @Test
    public void testGetQuoteBySymbol_MalformedData() throws Exception {
        String symbol = "AAPL";
        String responseBody = "Malformed JSON";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(responseBody);
        when(objectMapper.readValue(anyString(), eq(APIResponse.class)))
                .thenThrow(new IllegalArgumentException("Malformed JSON"));

        StockApiException thrown = assertThrows(StockApiException.class, () -> stockQuoteService.getQuoteBySymbol(symbol));
        assertTrue(thrown.getMessage().contains("An unexpected error occurred while fetching stock data"));
    }

    /**
     * Tests handling of empty API responses.
     * Verifies that StockApiException is thrown when response is empty.
     */
    @Test
    public void testGetQuoteBySymbol_EmptyResponse() {
        String symbol = "AAPL";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("");

        StockApiException thrown = assertThrows(StockApiException.class, () -> stockQuoteService.getQuoteBySymbol(symbol));
        assertEquals("API returned an empty response.", thrown.getMessage());
    }
}
