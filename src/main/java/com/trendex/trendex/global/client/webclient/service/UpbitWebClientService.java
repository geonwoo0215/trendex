package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.coinone.CoinoneCurrency;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitCandleData;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitOrderBook;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitTicker;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitWebClientService {

    private final WebClient upbitWebClient;

    public List<UpbitTicker> getTicker(String markets) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker")
                        .queryParam("markets",markets)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitTicker.class)
                .collectList()
                .block();

    }

    public List<UpbitTrade> getTrades(String market) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trades/ticks")
                        .queryParam("market",market)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitTrade.class)
                .collectList()
                .block();

    }

    public List<UpbitOrderBook> getOrderBook(String markets) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orderbook")
                        .queryParam("markets",markets)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitOrderBook.class)
                .collectList()
                .block();

    }

    public List<UpbitCandleData> getMinuteCandle(int units, String market, int count) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/candles/minutes/{unit}")
                        .queryParam("market", market)
                        .queryParam("count", count)
                        .build(units))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitCandleData.class)
                .collectList()
                .block();

    }

}
