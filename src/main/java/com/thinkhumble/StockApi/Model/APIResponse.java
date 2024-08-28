package com.thinkhumble.StockApi.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Model class representing the API response structure for stock data.
 */
public class APIResponse {
    @JsonProperty("Meta Data")
    private Map<String, Object> metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, Map<String, String>> timeSeriesDaily;

//    @JsonProperty("Information")
//    private String information;

    // Getters and Setters
    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    public Map<String, Map<String, String>> getTimeSeriesDaily() {
        return timeSeriesDaily;
    }

    public void setTimeSeriesDaily(Map<String, Map<String, String>> timeSeriesDaily) {
        this.timeSeriesDaily = timeSeriesDaily;
    }


}
