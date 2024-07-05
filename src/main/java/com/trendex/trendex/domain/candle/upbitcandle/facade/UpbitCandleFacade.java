package com.trendex.trendex.domain.candle.upbitcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisService;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
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
public class UpbitCandleFacade {

    private final UpbitCandleService upbitCandleService;

    private final UpbitCandleFetchService upbitCandleFetchService;

    private final UpbitSymbolService upbitSymbolService;

    private final CandleAnalysisService candleAnalysisService;

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchSaveAndAnalyzeUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();

        Flux<UpbitCandle> upbitCandlesFlux = upbitCandleFetchService.fetchUpbitData(upbitSymbols).share();

        upbitCandlesFlux
                .buffer(100)
                .flatMap(upbitCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitCandleService.saveAll(upbitCandles))))
                .subscribe();

        upbitCandlesFlux
                .flatMap(upbitCandle ->
                        Mono.fromFuture(CompletableFuture.supplyAsync(() ->
                                        upbitCandleService.getCandlesByMarketAndTime(upbitCandle.getMarket())))
                                .map(upbitCandleMappings ->
                                        candleAnalysisService.isVolumeSpike(upbitCandleMappings, upbitCandle.getVolume())))
                .subscribe();
    }

}
