package com.thinkhumble.StockApi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions across the entire application.
 * This class provides centralized exception handling and returns appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles {@link StockApiException} and returns a structured error response.
     *
     * @param ex The StockApiException thrown during API operations
     * @return ResponseEntity containing error message and HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(StockApiException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleStockApiException(StockApiException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        // Return the error response with HTTP status 500
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link StockNotFoundException} and returns a structured error response.
     *
     * @param ex The StockNotFoundException thrown when a stock is not found
     * @return ResponseEntity containing error message and HTTP status 404 (Not Found)
     */
    @ExceptionHandler(StockNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleStockNotFoundException(StockNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        // Return the error response with HTTP status 404
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles any other exceptions and returns a general error response.
     *
     * @param ex The generic Exception thrown
     * @return ResponseEntity containing a general error message and HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An unexpected error occurred: " + ex.getMessage());
        // Return the error response with HTTP status 500
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

