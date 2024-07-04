package com.trendex.trendex.domain.trade.bithumbtrade.service;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BithumbTradeFetchService {

    private final BithumbWebClientService bithumbWebClientService;

    public Flux<BithumbTrade> fetchBithumbData(List<BithumbSymbol> bithumbSymbols) {

        return Flux.fromIterable(bithumbSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(bithumbSymbol ->
                        bithumbWebClientService.getTransactionHistory(bithumbSymbol.getSymbol(), "KRW", 100)
                                .flatMapMany(transactionHistory ->
                                        Flux.fromIterable(Optional.ofNullable(transactionHistory.getData()).orElse(Collections.emptyList()))
                                                .map(bithumbTransactionHistoryData ->
                                                        bithumbTransactionHistoryData.toBithumbTrade(bithumbSymbol.getSymbol()))
                                )
                )
                .sequential();
    }

}
