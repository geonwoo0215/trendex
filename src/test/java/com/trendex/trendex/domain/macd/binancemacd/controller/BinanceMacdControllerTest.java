package com.trendex.trendex.domain.macd.binancemacd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.binancesymbol.fixture.BinanceSymbolFixture;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolRepository;
import com.trendex.trendex.domain.macd.binancemacd.fixture.BinanceMacdFixture;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import com.trendex.trendex.domain.macd.binancemacd.repository.BinanceMacdRepository;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import com.trendex.trendex.global.client.webclient.service.TelegramWebClientService;
import com.trendex.trendex.global.common.util.RedisUtil;
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
class BinanceMacdControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BinanceSymbolRepository binanceSymbolRepository;

    @Autowired
    BinanceMacdRepository binanceMacdRepository;

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @MockBean
    RedisUtil redisUtil;

    @Test
    @Transactional
    void 전체_MACD값_조회_API_성공() throws Exception {

        List<String> markets = List.of("BTC", "ADA", "SOR");

        List<BinanceSymbol> binanceSymbols = BinanceSymbolFixture.createBinanceSymbols(markets);
        binanceSymbolRepository.saveAll(binanceSymbols);

        Long endTime = 5L;
        List<BinanceMacd> binanceMacds = BinanceMacdFixture.createBinanceMacds(markets, endTime);
        binanceMacdRepository.saveAll(binanceMacds);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/macds/binance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].market", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].decision", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].macdValue", Matchers.everyItem(Matchers.isA(Double.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].macdSignalValue", Matchers.everyItem(Matchers.isA(Double.class))))
                .andDo(MockMvcResultHandlers.print());
    }

}