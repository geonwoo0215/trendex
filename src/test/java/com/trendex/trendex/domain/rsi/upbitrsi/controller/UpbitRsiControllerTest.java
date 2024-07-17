package com.trendex.trendex.domain.rsi.upbitrsi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.rsi.upbitrsi.fixture.UpbitRsiFixture;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.rsi.upbitrsi.repository.UpbitRsiRepository;
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
class UpbitRsiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @Autowired
    UpbitRsiRepository upbitRsiRepository;

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @Test
    @Transactional
    void 전체_RSI값_조회_API_성공() throws Exception {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        List<UpbitMarket> upbitMarkets = UpbitMarketFixture.createUpbitMarkets(markets);
        upbitMarketRepository.saveAll(upbitMarkets);

        Long latestTime = 5L;
        List<UpbitRsi> upbitRsis = UpbitRsiFixture.createUpbitRsis(markets, latestTime);
        upbitRsiRepository.saveAll(upbitRsis);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/rsis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].market", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].decision", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].value", Matchers.everyItem(Matchers.isA(Double.class))))
                .andDo(MockMvcResultHandlers.print());
    }

}