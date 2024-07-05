package com.trendex.trendex.domain.candle.coinonecandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisService;
import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import com.trendex.trendex.domain.candle.coinonecandle.service.CoinoneCandleFetchService;
import com.trendex.trendex.domain.candle.coinonecandle.service.CoinoneCandleService;
import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneCandleFacade {

    private final CoinoneCandleService coinoneCandleService;

    private final CoinoneCandleFetchService coinoneCandleFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    private final CandleAnalysisService candleAnalysisService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();

        Flux<CoinoneCandle> coinoneCandleFlux = coinoneCandleFetchService.fetchCoinoneData(coinoneSymbols).share();

        coinoneCandleFlux
                .buffer(100)
                .flatMap(coinoneCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> coinoneCandleService.saveAll(coinoneCandles))))
                .subscribe();

        coinoneCandleFlux
                .flatMap(coinoneCandle ->
                        Mono.fromFuture(CompletableFuture.supplyAsync(() ->
                                        coinoneCandleService.getCandlesBySymbolAndTime(coinoneCandle.getSymbol())))
                                .map(coinoneCandleMappings ->
                                        candleAnalysisService.isVolumeSpike(coinoneCandleMappings, Double.parseDouble(coinoneCandle.getVolume()))))
                .subscribe();

        log.info("coinone fetch done");
    }

}
