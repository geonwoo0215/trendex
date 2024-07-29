package com.trendex.trendex.domain.trade.binancetrade.service;

import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceTradeFetchService {

    private final BinanceWebClientService binanceWebClientService;

    public Flux<BinanceTrade> fetchBinanceData(List<String> binanceSymbols) {

        return Flux.fromIterable(binanceSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(binanceSymbol ->
                        binanceWebClientService.getTrade(binanceSymbol)
                                .flatMapMany(Flux::fromIterable)
                                .map(binanceTradeResponse -> binanceTradeResponse.toBinanceTrade(binanceSymbol))
                )
                .sequential();

    }
}

