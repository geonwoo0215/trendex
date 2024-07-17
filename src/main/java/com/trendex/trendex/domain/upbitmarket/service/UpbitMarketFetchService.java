package com.trendex.trendex.domain.upbitmarket.service;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitMarketFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public List<UpbitMarket> fetchUpbitData() {

        return upbitWebClientService.getMarketCode()
                .flatMap(upbitMarketCodes -> Mono.just(
                        upbitMarketCodes.stream()
                                .map(upbitMarketCode -> new UpbitMarket(upbitMarketCode.getMarket()))
                                .collect(Collectors.toList())
                ))
                .block();

    }
}
