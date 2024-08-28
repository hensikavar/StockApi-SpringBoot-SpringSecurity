package com.thinkhumble.StockApi.Exception;

/**
 * Custom exception class for handling errors related to stock API operations.
 */
public class StockApiException extends RuntimeException {
    /**
     * Constructor for creating a new StockApiException with a specific message.
     *
     * @param message The error message to include with the exception
     */
    public StockApiException(String message) {
        super(message);
    }
}