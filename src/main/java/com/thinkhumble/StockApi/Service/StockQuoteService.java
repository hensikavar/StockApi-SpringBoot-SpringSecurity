package com.thinkhumble.StockApi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkhumble.StockApi.Exception.StockApiException;
import com.thinkhumble.StockApi.Model.APIResponse;
import com.thinkhumble.StockApi.Model.StockQuote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Service class for fetching and processing stock quotes from an external API.
 */
@Service
public class StockQuoteService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String baseUrl;


    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public StockQuoteService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Default constructor for testing purposes
    public StockQuoteService() {}

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Fetches a stock quote for a given symbol from an external API.
     * The result is cached to improve performance and reduce API calls.
     *
     * @param symbol The stock symbol to fetch the quote for
     * @return StockQuote object containing the stock data
     */
    @Cacheable(value = "stockQuotes", key = "#symbol")

    public StockQuote getQuoteBySymbol(String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey)
                .toUriString();
        System.out.println(url);
        try {
            // Fetch response from API
            String responseBody = restTemplate.getForObject(url, String.class);
            if (responseBody == null || responseBody.isEmpty()) {
                throw new RuntimeException("API returned an empty response.");
            }

            // Parse the API response
            ObjectMapper objectMapper = new ObjectMapper();
            APIResponse apiResponse = objectMapper.readValue(responseBody, APIResponse.class);

            if (apiResponse == null || apiResponse.getTimeSeriesDaily() == null || apiResponse.getTimeSeriesDaily().isEmpty()) {
                throw new RuntimeException("Failed to fetch valid stock data.");
            }

            // Extract data from response
            Map<String, String> latestData = apiResponse.getTimeSeriesDaily().entrySet().iterator().next().getValue();

            StockQuote stockQuote = new StockQuote();
            stockQuote.setSymbol(symbol);
            stockQuote.setPrice(Double.parseDouble(latestData.get("4. close")));

            double openPrice = Double.parseDouble(latestData.get("1. open"));
            double closePrice = Double.parseDouble(latestData.get("4. close"));
            double change = closePrice - openPrice;
            double percentageChange = (change / openPrice) * 100;

            // Format change and percentage change
            DecimalFormat df = new DecimalFormat("#.##");
            stockQuote.setChange(roundToTwoDecimalPlaces(change));
            stockQuote.setPercentageChange(roundToTwoDecimalPlaces(percentageChange));
            stockQuote.setTimestamp(apiResponse.getTimeSeriesDaily().keySet().iterator().next());
            stockQuote.setOpenPrice(Double.parseDouble(latestData.get("1. open")));
            stockQuote.setHighPrice(Double.parseDouble(latestData.get("2. high")));
            stockQuote.setLowPrice(Double.parseDouble(latestData.get("3. low")));
            stockQuote.setVolume(Long.parseLong(latestData.get("5. volume")));
            stockQuote.setPreviousClose(Double.parseDouble(latestData.get("4. close")));

            return stockQuote;

        } catch (HttpClientErrorException e) {
            // Handle HTTP errors such as 404 or 500
            throw new StockApiException("HTTP error occurred while fetching stock data: " + e.getMessage());
        } catch (Exception e) {
            // Handle general errors
            throw new StockApiException("An unexpected error occurred while fetching stock data for symbol: " + symbol);
        }

    }

    /**
     * Rounds a double value to two decimal places.
     *
     * @param value The value to round
     * @return The value rounded to two decimal places
     */
    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}