/*
package com.exchange.reactive.web;

import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.service.exchange.ExchangeService;
import com.exchange.reactive.webdto.request.AddExchangeWebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeRestController.class)
public class ExchangeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    public void AddExchange_Success_Return201() throws Exception {

        ExchangeResponse exchangeResponse = new ExchangeResponse("1", 200,"S","D",50.0,4.0);
        when(exchangeService.addExchange(any(AddExchangeRequest.class)))
                .thenReturn(Single.just(exchangeResponse));

        MvcResult mvcResult = mockMvc.perform(post("/api/exchanges")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AddExchangeWebRequest())))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", equalTo(200.0)))
                .andExpect(jsonPath("$.currencyOrigin", equalTo("S")));

        verify(exchangeService, times(1)).addExchange(any(AddExchangeRequest.class));
    }

    @Test
    public void GetAllExchanges_LimitAndPageSpecified_Success_Return200WithListOfBookWebResponse() throws Exception {
        when(exchangeService.getAllExchanges(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(new ExchangeResponse("1", 200,"S","D",50.0,4.0))));

        MvcResult mvcResult = mockMvc.perform(get("/api/exchanges?limit=5&page=0")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode", nullValue()))
                .andExpect(jsonPath("$.data[0].id", equalTo("1")));

        verify(exchangeService, times(1)).getAllExchanges(anyInt(), anyInt());
    }

    @Test
    public void GetAllExchanges_LimitAndPageNotSpecified_Success_Return200WithListOfBookWebResponse() throws Exception {
        when(exchangeService.getAllExchanges(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(new ExchangeResponse("1", 200,"S","D",50.0,4.0))));

        MvcResult mvcResult = mockMvc.perform(get("/api/exchanges")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode", nullValue()))
                .andExpect(jsonPath("$.data[0].id", equalTo("1")));

        verify(exchangeService, times(1)).getAllExchanges(anyInt(), anyInt());
    }

}*/
