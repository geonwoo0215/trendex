package com.trendex.trendex.domain.orderbooktradeaggregate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.domain.orderbooktradeaggregate.fixture.OrderBookTradeAggregateFixture;
import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;
import com.trendex.trendex.domain.orderbooktradeaggregate.repository.OrderbookTradeAggregateRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderbookTradeAggregateControllerTest {

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
    OrderbookTradeAggregateRepository orderbookTradeAggregateRepository;

    @Test
    @Transactional
    void 전체_거래량_매도매수량_조회_API_성공() throws Exception {

        String market = "KRW-BTC";
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        OrderbookTradeAggregate orderbookTradeAggregate = OrderBookTradeAggregateFixture.createOrderbookTradeAggregate(market, startTime);
        orderbookTradeAggregateRepository.save(orderbookTradeAggregate);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("market", market);
        params.add("startTime", String.valueOf(startTime));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/aggregate/upbit")
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