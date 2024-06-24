package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.binance.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceWebClientService {

    private final WebClient binanceWebClient;

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

    public List<BinanceTickerPrice> getTickerPrice() {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/price")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(BinanceTickerPrice.class)
                .collectList()
                .block();

    }

    public BinanceOrderBook getOrderBook(String symbol) {

        return binanceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/depth")
                        .queryParam("symbol",symbol)
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

}
