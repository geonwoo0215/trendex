package com.trendex.trendex.domain.candle.bithumbcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisService;
import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleFetchService;
import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleService;
import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BithumbCandleFacade {

    private final BithumbCandleService bithumbCandleService;

    private final BithumbSymbolService bithumbSymbolService;

    private final BithumbCandleFetchService bithumbCandleFetchService;

    private final CandleAnalysisService candleAnalysisService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();

        Flux<BithumbCandle> bithumbCandleFlux = bithumbCandleFetchService.fetchBithumbData(bithumbSymbols).share();

        bithumbCandleFlux
                .buffer(1000)
                .flatMap(bithumbCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> bithumbCandleService.saveAll(bithumbCandles))))
                .subscribe();

        bithumbCandleFlux
                .flatMap(bithumbCandle ->
                        Mono.fromFuture(CompletableFuture.supplyAsync(() ->
                                        bithumbCandleService.getCandlesBySymbolAndTime(bithumbCandle.getSymbol())))
                                .map(bithumbCandleMappings ->
                                        candleAnalysisService.isVolumeSpike(bithumbCandleMappings, Double.parseDouble(bithumbCandle.getVolume()))))
                .subscribe();

        log.info("bithumb fetch done");
    }
}
