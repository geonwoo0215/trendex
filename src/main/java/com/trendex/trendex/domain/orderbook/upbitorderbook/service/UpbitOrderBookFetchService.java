package com.trendex.trendex.domain.orderbook.upbitorderbook.service;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitOrderBookFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitOrderBook> fetchUpbitData(List<UpbitSymbol> upbitSymbols) {
        return Flux.fromIterable(upbitSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getOrderBook(upbitSymbol.getSymbol())
                                .flatMapMany(Flux::fromIterable)
                                .flatMap(upbitOrderBookResponse ->
                                        Flux.fromStream(
                                                upbitOrderBookResponse.getOrderBookUnits().stream()
                                                        .map(upbitOrderBookData -> upbitOrderBookData.toUpbitOrderBook(upbitSymbol.getSymbol()))
                                        )
                                )
                )
                .sequential();
    }

}
