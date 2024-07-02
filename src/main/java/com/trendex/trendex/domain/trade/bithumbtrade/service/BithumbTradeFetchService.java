package com.trendex.trendex.domain.trade.bithumbtrade.service;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbTradeFetchService {

    private final BithumbWebClientService bithumbWebClientService;

    public List<BithumbTrade> fetchBithumbData(List<BithumbSymbol> bithumbSymbols) {

        return Flux.fromIterable(bithumbSymbols)
                .flatMap(bithumbSymbol ->
                        bithumbWebClientService.getTransactionHistory(bithumbSymbol.getSymbol(), "KRW", 100)
                                .flatMapMany(transactionHistory -> Flux.fromIterable(transactionHistory.getData())
                                        .map(bithumbTransactionHistoryData ->
                                                bithumbTransactionHistoryData.toBithumbTrade(bithumbSymbol.getSymbol()))
                                )
                )
                .collectList()
                .block();

    }

}
