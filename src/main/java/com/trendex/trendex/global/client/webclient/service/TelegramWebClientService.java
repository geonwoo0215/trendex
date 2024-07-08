package com.trendex.trendex.global.client.webclient.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TelegramWebClientService {

    private final WebClient telegramWebClient;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.chat-id}")
    private String chatId;

    public TelegramWebClientService(@Qualifier("telegramWebClient") WebClient telegramWebClient) {
        this.telegramWebClient = telegramWebClient;
    }

    public Mono<Void> sendMessage(String text) {

        return telegramWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/bot{token}/sendmessage")
                        .queryParam("text", text)
                        .queryParam("chat_id", chatId)
                        .build(token))
                .header("accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException()))
                .bodyToMono(void.class);
    }


}
