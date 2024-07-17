package com.trendex.trendex.global.client.webclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramWebClientService telegramWebClientService;

    private final String VOLUME_SPIKE_MESSAGE = "거래량이급증하였습니다.";

    public Mono<Void> sendVolumeSpike() {
        return telegramWebClientService.sendMessage(VOLUME_SPIKE_MESSAGE);
    }
}
