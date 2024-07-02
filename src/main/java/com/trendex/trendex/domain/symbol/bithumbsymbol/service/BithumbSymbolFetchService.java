package com.trendex.trendex.domain.symbol.bithumbsymbol.service;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BithumbSymbolFetchService {

    private final BithumbWebClientService bithumbWebClientService;

    public List<BithumbSymbol> fetchBithumbData() {

        return bithumbWebClientService.getWithdrawMinimum("ALL")
                .flatMap(bithumbWithdrawMinimum -> {
                    List<BithumbSymbol> symbols = bithumbWithdrawMinimum.getData().stream()
                            .map(currencyData -> new BithumbSymbol(currencyData.getCurrency()))
                            .collect(Collectors.toList());
                    return Mono.just(symbols);
                })
                .block();

    }
}
