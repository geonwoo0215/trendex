package com.trendex.trendex.global.client.webclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendex.trendex.global.client.webclient.dto.upbit.*;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UpbitWebClientService {

    private final WebClient upbitWebClient;

    private final JwtTokenProvider jwtTokenProvider;

    public UpbitWebClientService(@Qualifier("upbitWebClient") WebClient upbitWebClient, JwtTokenProvider jwtTokenProvider) {
        this.upbitWebClient = upbitWebClient;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<UpbitTicker> getTicker(String markets) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker")
                        .queryParam("markets", markets)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitTicker.class)
                .collectList()
                .block();

    }

    @RateLimiter(name = "upbit")
    public Mono<List<UpbitTradeResponse>> getTrades(String market) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trades/ticks")
                        .queryParam("market", market)
                        .queryParam("count", 100)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitTradeResponse.class)
                .collectList();

    }

    @RateLimiter(name = "upbit")
    public Mono<List<UpbitOrderBookResponse>> getOrderBook(String markets) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orderbook")
                        .queryParam("markets", markets)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitOrderBookResponse.class)
                .collectList();

    }

    @RateLimiter(name = "upbit")
    public Mono<List<UpbitCandleResponse>> getMinuteCandle(int units, String market, int count) {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/candles/minutes/{unit}")
                        .queryParam("market", market)
                        .queryParam("count", count)
                        .build(units))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitCandleResponse.class)
                .collectList();

    }

    @RateLimiter(name = "upbit")
    public Mono<List<UpbitMarketCode>> getMarketCode() {

        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/market/all")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToFlux(UpbitMarketCode.class)
                .collectList();

    }

    public Mono<List<UpbitAccountResponse>> getAccounts() {
        String jwtToken = jwtTokenProvider.createToken();


        return upbitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts")
                        .build())
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("4xx error: " + errorBody); // 로그에 에러 메시지 출력
                                    return Mono.error(new RuntimeException("4xx error: " + errorBody));
                                })
                )
                .bodyToFlux(UpbitAccountResponse.class)
                .collectList();
    }

    public Mono<UpbitOrderResponse> getOrders(String market, String side, String volume, String price, String ordType, String timeInForce) {

        log.info("{}", market);
        log.info("{}", side);
        log.info("{}", volume);
        log.info("{}", price);
        log.info("{}", ordType);
        log.info("{}", timeInForce);

        String jwtToken = jwtTokenProvider.createToken(market, side, volume, price, ordType, timeInForce);

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("market", market);
        bodyParams.put("side", side);
        if (volume != null) {
            bodyParams.put("volume", volume);

        }
        if (price != null) {
            bodyParams.put("price", price);

        }
        bodyParams.put("ord_type", ordType);
        bodyParams.put("time_in_force", timeInForce);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(bodyParams);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert map to JSON", e);
        }

        log.info("jsonbody = {}", jsonBody);
        return upbitWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/orders")
                        .build())
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(jsonBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("4xx error: " + errorBody); // 로그에 에러 메시지 출력
                                    return Mono.error(new RuntimeException("4xx error: " + errorBody));
                                })
                )
                .bodyToMono(UpbitOrderResponse.class);

    }

}
