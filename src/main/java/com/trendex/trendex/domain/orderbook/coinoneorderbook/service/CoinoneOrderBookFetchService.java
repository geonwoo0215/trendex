package com.trendex.trendex.domain.orderbook.coinoneorderbook.service;

import com.trendex.trendex.domain.orderbook.coinoneorderbook.model.CoinoneOrderBook;
import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneOrderBookFetchService {

    private final CoinoneWebClientService coinoneWebClientService;

    public Flux<CoinoneOrderBook> fetchCoinoneData(List<CoinoneSymbol> coinoneSymbols) {
        return Flux.fromIterable(coinoneSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(coinoneSymbol ->
                        coinoneWebClientService.getOrderBook("KRW", coinoneSymbol.getSymbol(), 15)
                                .flatMapMany(orderBookResponse -> Flux.fromIterable(orderBookResponse.toCoinoneOrderBook()))
                )
                .sequential();
    }

}
