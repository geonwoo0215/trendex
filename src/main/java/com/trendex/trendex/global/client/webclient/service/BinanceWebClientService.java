package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.binance.*;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class BinanceWebClientService {

    private final WebClient binanceWebClient;

    public BinanceWebClientService(@Qualifier("binanceWebClient") WebClient binanceWebClient) {
        this.binanceWebClient = binanceWebClient;
    }

    public List<BinanceTrade> getTrade(String symbol) {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trades")
                        .queryParam("symbol", symbol)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(BinanceTrade.class)
                .collectList()
                .block();

    }

    public List<BinanceTicker> getTicker() {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/24hr")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(BinanceTicker.class)
                .collectList()
                .block();

    }

    @RateLimiter(name = "binance")
    public Mono<List<BinanceTickerPrice>> getTickerPrice() {
        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/price")
                        .build()
                )
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(BinanceTickerPrice.class)
                .collectList();
    }

    public BinanceOrderBook getOrderBook(String symbol) {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/depth")
                        .queryParam("symbol", symbol)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BinanceOrderBook.class)
                .block();

    }

    public List<BinanceOrderBookTicker> getOrderBookTicker() {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/bookTicker")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(BinanceOrderBookTicker.class)
                .collectList()
                .block();

    }

    @RateLimiter(name = "binance")
    public Mono<List<List<Object>>> getCandle(String symbol, String interval, int limit) {
        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .build()
                )
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Client error")))
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

}
