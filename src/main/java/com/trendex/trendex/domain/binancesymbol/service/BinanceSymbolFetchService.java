package com.trendex.trendex.domain.binancesymbol.service;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceSymbolFetchService {

    private final BinanceWebClientService binanceWebClientService;

    public List<BinanceSymbol> fetchBinanceData() {

        return binanceWebClientService.getTickerPrice()
                .flatMap(binanceTickerPrices -> Mono.just(
                        binanceTickerPrices.stream()
                                .map(binanceTickerPrice -> new BinanceSymbol(binanceTickerPrice.getSymbol()))
                                .collect(Collectors.toList())
                ))
                .block();

    }
}
