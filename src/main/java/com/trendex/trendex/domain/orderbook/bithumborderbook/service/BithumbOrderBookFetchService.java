package com.trendex.trendex.domain.orderbook.bithumborderbook.service;

import com.trendex.trendex.domain.orderbook.bithumborderbook.model.BithumbOrderBook;
import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbOrderBookFetchService {

    private final BithumbWebClientService bithumbWebClientService;

    public Flux<BithumbOrderBook> fetchBithumbData(List<BithumbSymbol> bithumbSymbols) {

        return Flux.fromIterable(bithumbSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(bithumbSymbol ->
                        bithumbWebClientService.getOrderBook(bithumbSymbol.getSymbol(), "KRW", 30)
                                .flatMapMany(bithumbOrderBookResponse -> Flux.fromIterable(bithumbOrderBookResponse.getData().toBithumbOrderBook())))
                .sequential();

    }

}
