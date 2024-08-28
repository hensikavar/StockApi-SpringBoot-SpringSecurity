package com.thinkhumble.StockApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // This will configure MockMvc and Spring Security
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        // Initialize MockMvc with Spring Security
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetQuoteBySymbolWithValidUser() throws Exception {
        mockMvc.perform(get("/api/stocks/AAPL"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetQuoteBySymbolWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/stocks/AAPL"))
                .andExpect(status().isUnauthorized());
    }
}
