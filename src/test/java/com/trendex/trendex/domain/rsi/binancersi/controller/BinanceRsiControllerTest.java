package com.trendex.trendex.domain.rsi.binancersi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.binancesymbol.fixture.BinanceSymbolFixture;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolRepository;
import com.trendex.trendex.domain.rsi.binancersi.fixture.BinanceRsiFixture;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.repository.BinanceRsiRepository;
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
class BinanceRsiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BinanceSymbolRepository binanceSymbolRepository;

    @Autowired
    BinanceRsiRepository binanceRsiRepository;

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @MockBean
    RedisUtil redisUtil;

    @Test
    @Transactional
    void 전체_RSI값_조회_API_성공() throws Exception {

        List<String> symbols = List.of("BTC", "ADA", "SOR");

        List<BinanceSymbol> binanceSymbols = BinanceSymbolFixture.createBinanceSymbols(symbols);
        binanceSymbolRepository.saveAll(binanceSymbols);

        Long latestTime = 5L;
        List<BinanceRsi> binanceRsis = BinanceRsiFixture.createBinanceRsis(symbols, latestTime);
        binanceRsiRepository.saveAll(binanceRsis);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/rsis/binance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].market", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].decision", Matchers.everyItem(Matchers.isA(String.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].value", Matchers.everyItem(Matchers.isA(Double.class))))
                .andDo(MockMvcResultHandlers.print());
    }

}