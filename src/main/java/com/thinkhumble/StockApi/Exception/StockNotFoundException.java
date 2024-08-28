package com.thinkhumble.StockApi.Exception;
/**
 * Custom exception class for handling errors related to stock not found.
 */
public class StockNotFoundException extends RuntimeException {
    /**
     * Constructor for creating a new StockNotFoundException with a specific message.
     *
     * @param message The error message to include with the exception
     */
    public StockNotFoundException(String message) {
        super(message);
    }
}
