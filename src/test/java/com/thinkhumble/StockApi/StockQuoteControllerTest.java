package com.thinkhumble.StockApi;


import com.thinkhumble.StockApi.Controller.StockQuoteController;
import com.thinkhumble.StockApi.Model.StockQuote;
import com.thinkhumble.StockApi.Service.StockQuoteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;

@WebMvcTest(StockQuoteController.class)
public class StockQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StockQuoteService stockQuoteService;

    @InjectMocks
    private StockQuoteController stockController;

    @Test
    public void testGetQuoteBySymbol() throws Exception {
        StockQuote stockQuote = new StockQuote();
        stockQuote.setSymbol("AAPL");
        stockQuote.setPrice(150.00);

        given(stockQuoteService.getQuoteBySymbol("AAPL")).willReturn(stockQuote);

        mockMvc.perform(get("/api/stocks/AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.price").value(150.00));
    }
}

