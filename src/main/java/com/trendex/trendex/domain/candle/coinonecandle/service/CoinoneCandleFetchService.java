package com.trendex.trendex.domain.candle.coinonecandle.service;

import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoinoneCandleFetchService {

    private final CoinoneWebClientService coinoneWebClientService;

    public Flux<CoinoneCandle> fetchCoinoneData(List<CoinoneSymbol> coinoneSymbols) {

        return Flux.fromIterable(coinoneSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(coinoneSymbol ->
                        coinoneWebClientService.getCandle("KRW", coinoneSymbol.getSymbol(), "3m", 20)
                                .flatMapMany(coinoneCandle ->
                                        Optional.ofNullable(coinoneCandle.getChart())
                                                .map(chart -> Flux.fromIterable(chart)
                                                        .map(chartData -> chartData.toCoinoneCandle(coinoneSymbol.getSymbol()))
                                                )
                                                .orElseGet(Flux::empty)
                                )
                )
                .sequential();

    }
}
