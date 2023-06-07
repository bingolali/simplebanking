package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.request.AccountCreditRequest;
import com.eteration.simplebanking.request.AccountDebitRequest;
import com.eteration.simplebanking.response.AccountCreditResponse;
import com.eteration.simplebanking.response.AccountDebitResponse;
import com.eteration.simplebanking.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Test
    public void testCreditEndpoint_ReturnsHttpStatusOkIfAccountNumberExists() throws Exception {

        AccountCreditRequest accountCreditRequest = new AccountCreditRequest(1000.0);

        MvcResult result = mockMvc.perform(post("/account/v1/credit/17892")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountCreditRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        AccountCreditResponse response = objectMapper.readValue(responseBody, AccountCreditResponse.class);
        String status = response.getStatus();
        assertEquals("OK", status);
    }

    @Test
    public void  should_return_http_status_not_found_when_account_number_does_not_exist() throws Exception {

        AccountCreditRequest accountCreditRequest = new AccountCreditRequest(1000.0);

        mockMvc.perform(post("/account/v1/credit/2349")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountCreditRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDebitEndpoint_ReturnsHttpStatusOkIfAccountNumberExists() throws Exception {

        AccountDebitRequest accountDebitRequest = new AccountDebitRequest(50.0);

        MvcResult result = mockMvc
                .perform(post("/account/v1/debit/17892")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDebitRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        AccountDebitResponse response = objectMapper.readValue(responseBody, AccountDebitResponse.class);
        String status = response.getStatus();

        assertEquals("OK", status);
    }

    @Test
    public void should_return_insufficient_balance_exception_when_insufficient_balance_occurs() throws Exception {

        AccountDebitRequest accountDebitRequest = new AccountDebitRequest(5000.0);

        mockMvc.perform(post("/account/v1/debit/17892")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDebitRequest)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InsufficientBalanceException));
    }

    @Test
    public void should_return_http_status_ok_when_account_exists() throws Exception {

        mockMvc.perform(get("/account/v1/{accountNumber}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value("Jim"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(700));
    }
}
