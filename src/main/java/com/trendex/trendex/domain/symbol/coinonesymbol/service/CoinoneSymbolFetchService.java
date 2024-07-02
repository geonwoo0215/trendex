package com.trendex.trendex.domain.symbol.coinonesymbol.service;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneSymbolFetchService {

    private final CoinoneWebClientService coinoneWebClientService;

    public List<CoinoneSymbol> fetchCoinoneData() {
        return coinoneWebClientService.getAllCurrencies()
                .flatMap(coinoneCurrency -> {
                    List<CoinoneSymbol> symbols = coinoneCurrency.getCurrencies().stream()
                            .map(coinoneCurrencyDetail -> new CoinoneSymbol(coinoneCurrencyDetail.getSymbol()))
                            .collect(Collectors.toList());
                    return Mono.just(symbols);
                })
                .block();
    }
}
