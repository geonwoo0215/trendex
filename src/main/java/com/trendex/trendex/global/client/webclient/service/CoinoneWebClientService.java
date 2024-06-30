package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.coinone.*;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CoinoneWebClientService {

    private final WebClient coinoneWebClient;

    public CoinoneWebClientService(@Qualifier("coinoneWebClient") WebClient coinoneWebClient) {
        this.coinoneWebClient = coinoneWebClient;
    }

    @RateLimiter(name = "coinone")
    public Mono<CoinoneCurrency> getAllCurrencies() {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/currencies")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneCurrency.class);

    }

    public CoinoneCurrency getCurrencies(String currency) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/currencies/{currency}")
                        .build(currency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneCurrency.class)
                .block();

    }

    public CoinoneTicker getAllTicker(String quoteCurrency, boolean additionalData) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker_new/{quote_currency}")
                        .queryParam("additional_data", additionalData)
                        .build(quoteCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneTicker.class)
                .block();

    }

    public CoinoneTicker getTicker(String quoteCurrency, String targetCurrency, boolean additionalData) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker_new/{quote_currency}/{target_currency}")
                        .queryParam("additional_data", additionalData)
                        .build(quoteCurrency, targetCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneTicker.class)
                .block();

    }

    public CoinoneTransactionHistory getTransactionHistory(String quoteCurrency, String targetCurrency, int size) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trades/{quote_currency}/{target_currency}")
                        .queryParam("size", size)
                        .build(quoteCurrency, targetCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneTransactionHistory.class)
                .block();

    }

    public CoinoneOrderBook getOrderBook(String quoteCurrency, String targetCurrency, int size) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orderbook/{quote_currency}/{target_currency}")
                        .queryParam("size", size)
                        .build(quoteCurrency, targetCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneOrderBook.class)
                .block();

    }

    @RateLimiter(name = "coinone")
    public Mono<CoinoneCandle> getCandle(String quoteCurrency, String targetCurrency, String interval, int size) {

        return coinoneWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chart/{quote_currency}/{target_currency}")
                        .queryParam("interval", interval)
                        .queryParam("size", size)
                        .build(quoteCurrency, targetCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(CoinoneCandle.class);

    }

}
