package com.thinkhumble.StockApi.Controller;

import com.thinkhumble.StockApi.Model.StockQuote;
import com.thinkhumble.StockApi.Service.StockQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling stock quote requests.
 */
@RestController

public class StockQuoteController {
    @Autowired
    private StockQuoteService stockQuoteService;

    /**
     * Retrieves a stock quote for a given symbol.
     *
     * @param symbol The stock symbol to retrieve the quote for
     * @return ResponseEntity containing the stock quote
     */
    @GetMapping("quote/{symbol}")
    public ResponseEntity<StockQuote> getQuoteBySymbol(@PathVariable String symbol) {
        StockQuote stockQuote = stockQuoteService.getQuoteBySymbol(symbol);
        return ResponseEntity.ok(stockQuote);
    }

    /**
     * Retrieves stock quotes for multiple symbols.
     *
     * @param symbols List of stock symbols to retrieve quotes for
     * @return ResponseEntity containing the list of stock quotes
     */

    @GetMapping("/quotes")
    public ResponseEntity<List<StockQuote>> getBatchQuotesBySymbols(@RequestParam List<String> symbols) {
        List<StockQuote> stockQuotes = symbols.stream()
                .map(stockQuoteService::getQuoteBySymbol)
                .filter(quote -> quote != null) // Filter out nulls in case of failure
                .collect(Collectors.toList());

        if (stockQuotes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        return ResponseEntity.ok(stockQuotes);
    }
}
