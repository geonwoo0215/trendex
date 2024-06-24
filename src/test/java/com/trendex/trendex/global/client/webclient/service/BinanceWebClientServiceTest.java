package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.binance.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BinanceWebClientServiceTest {

    @Autowired
    BinanceWebClientService binanceWebClientService;

    @Test
    void dd() {
        List<BinanceTrade> btc = binanceWebClientService.getTrade("BTCUSDT");
        System.out.println(btc.get(0).isBuyerMaker());
    }

    @Test
    void ddd() {
        List<BinanceTicker> ticker = binanceWebClientService.getTicker();
        System.out.println(ticker.get(0).getAskPrice());
    }

    @Test
    void dddd() {
        List<BinanceTickerPrice> ticker = binanceWebClientService.getTickerPrice();
        System.out.println(ticker.get(0).getSymbol());
    }

    @Test
    void ddddd() {
        BinanceOrderBook orderBook = binanceWebClientService.getOrderBook("ETHBTC");
        System.out.println(orderBook.getAsks().get(0).get(0));
    }

    @Test
    void dddddd() {
        List<BinanceOrderBookTicker> orderBookTicker = binanceWebClientService.getOrderBookTicker();
        System.out.println(orderBookTicker.get(0).getSymbol());
    }
}