package com.trendex.trendex.domain.trade.upbittrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.orderbook.upbitorderbook.fiture.UpbitOrderBookFixture;
import com.trendex.trendex.domain.orderbook.upbitorderbook.fiture.UpbitOrderBookUnitFixture;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.repository.UpbitOrderBookRepository;
import com.trendex.trendex.domain.trade.upbittrade.fiture.UpbitTradeFixture;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeRepository;
import com.trendex.trendex.domain.upbitmarket.repository.UpbitMarketRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UpbitTradeControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @MockBean
    RedisUtil redisUtil;

    @Autowired
    UpbitTradeRepository upbitTradeRepository;

    @Autowired
    UpbitOrderBookRepository upbitOrderBookRepository;

    @Test
    @Transactional
    void 전체_거래량_매도매수량_조회_API_성공() throws Exception {

        String btcMarket = "KRW-BTC";
        String adaMarket = "KRW-ADA";
        String solMarket = "KRW-SOL";

        List<UpbitOrderBook> upbitBTCOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, btcMarket);
        upbitBTCOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });

        List<UpbitOrderBook> upbitADAOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, adaMarket);
        upbitADAOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });


        List<UpbitOrderBook> upbitSOLOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, solMarket);
        upbitSOLOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });

        List<UpbitTrade> upbitBTCTrades = UpbitTradeFixture.createUpbitTrades(10L, btcMarket);
        List<UpbitTrade> upbitADATrades = UpbitTradeFixture.createUpbitTrades(10L, adaMarket);
        List<UpbitTrade> upbitSOLTrades = UpbitTradeFixture.createUpbitTrades(10L, solMarket);

        upbitOrderBookRepository.saveAll(upbitBTCOrderBooks);
        upbitOrderBookRepository.saveAll(upbitADAOrderBooks);
        upbitOrderBookRepository.saveAll(upbitSOLOrderBooks);
        upbitTradeRepository.saveAll(upbitBTCTrades);
        upbitTradeRepository.saveAll(upbitADATrades);
        upbitTradeRepository.saveAll(upbitSOLTrades);


        LocalDateTime time = LocalDateTime.now();


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("market", btcMarket);
        params.add("startTime", String.valueOf(time));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/trades/upbit")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgTradePrice", Matchers.isA(Double.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalTradeVolume", Matchers.isA(Double.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgAskPrice", Matchers.isA(Double.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgBidPrice", Matchers.isA(Double.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAskSize", Matchers.isA(Double.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalBidSize", Matchers.isA(Double.class)))
                .andDo(MockMvcResultHandlers.print());
    }

}