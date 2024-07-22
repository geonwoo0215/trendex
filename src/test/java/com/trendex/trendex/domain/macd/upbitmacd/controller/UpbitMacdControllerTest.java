package com.trendex.trendex.domain.macd.upbitmacd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.macd.upbitmacd.fixture.UpbitMacdFixture;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpbitMacdRepository;
import com.trendex.trendex.domain.upbitmarket.fixture.UpbitMarketFixture;
import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.upbitmarket.repository.UpbitMarketRepository;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import com.trendex.trendex.global.client.webclient.service.TelegramWebClientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UpbitMacdControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @Autowired
    UpbitMacdRepository upbitMacdRepository;

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @Test
    @Transactional
    void 전체_MACD값_조회_API_성공() throws Exception {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        List<UpbitMarket> upbitMarkets = UpbitMarketFixture.createUpbitMarkets(markets);
        upbitMarketRepository.saveAll(upbitMarkets);

        Long endTime = 5L;
        List<UpbitMacd> upbitMacds = UpbitMacdFixture.createUpbitMacds(markets, endTime);
        upbitMacdRepository.saveAll(upbitMacds);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/macds/upbit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].market", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].decision", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].macdValue", Matchers.everyItem(Matchers.isA(Double.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].macdSignalValue", Matchers.everyItem(Matchers.isA(Double.class))))
                .andDo(MockMvcResultHandlers.print());
    }

}