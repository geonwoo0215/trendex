package com.trendex.trendex.domain.symbol.upbitsymbol.service;

import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
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
public class UpbitSymbolFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public List<UpbitSymbol> fetchUpbitData() {

        return upbitWebClientService.getMarketCode()
                .flatMap(upbitMarketCodes -> Mono.just(
                        upbitMarketCodes.stream()
                                .map(upbitMarketCode -> new UpbitSymbol(upbitMarketCode.getMarket()))
                                .collect(Collectors.toList())
                ))
                .block();

    }
}
