package com.trendex.trendex.global.client.webclient.service;

import com.trendex.trendex.global.client.webclient.dto.bithumb.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class BithumbWebClientService {

    private final WebClient bithumbWebClient;

    public BithumbTicker getAllTicker(String paymentCurrency) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/ALL_{payment_currency}")
                        .build(paymentCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbTicker.class)
                .block();

    }

    public BithumbTicker getTicker(String orderCurrency, String paymentCurrency) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/{order_currency}_{payment_currency}")
                        .build(orderCurrency,paymentCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbTicker.class)
                .block();

    }

    public BithumbAllOrderBook getAllOrderBook(String paymentCurrency, int count) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orderbook/ALL_{payment_currency}")
                        .queryParam("count", count)
                        .build(paymentCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbAllOrderBook.class)
                .block();

    }

    public BithumbOrderBook getOrderBook(String orderCurrency, String paymentCurrency, int count) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orderbook/{order_currency}_{payment_currency}")
                        .queryParam("count", count)
                        .build(orderCurrency, paymentCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbOrderBook.class)
                .block();

    }

    public BithumbTransactionHistory getTransactionHistory(String orderCurrency, String paymentCurrency, int count) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/transaction_history/{order_currency}_{payment_currency}")
                        .queryParam("count", count)
                        .build(orderCurrency, paymentCurrency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbTransactionHistory.class)
                .block();

    }

    public BithumbCandle getCandle(String orderCurrency, String paymentCurrency, String chartIntervals) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/candlestick/{order_currency}_{payment_currency}/{chart_intervals}")
                        .build(orderCurrency, paymentCurrency, chartIntervals))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbCandle.class)
                .block();

    }

    public BithumbWithdrawMinimum getWithdrawMinimum(String currency) {

        return bithumbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/withdraw/minimum/{currency}")
                        .build(currency))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(BithumbWithdrawMinimum.class)
                .block();

    }

}
