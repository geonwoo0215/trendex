package com.trendex.trendex.domain.orderbook.binanceorderbook.service;

import com.trendex.trendex.domain.orderbook.binanceorderbook.model.BinanceOrderBook;
import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceOrderBookFetchService {

    private final BinanceWebClientService binanceWebClientService;

    public Flux<BinanceOrderBook> fetchBinanceData(List<BinanceSymbol> binanceSymbols) {
        return Flux.fromIterable(binanceSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(binanceSymbol ->
                        binanceWebClientService.getOrderBook(binanceSymbol.getSymbol())
                                .flatMapMany(orderBookResponse -> Flux.fromIterable(orderBookResponse.toBinanceOrderBook(binanceSymbol.getSymbol())))
                )
                .sequential();
    }

}
