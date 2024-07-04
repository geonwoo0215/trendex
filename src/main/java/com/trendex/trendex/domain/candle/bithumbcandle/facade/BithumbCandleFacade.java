package com.trendex.trendex.domain.candle.bithumbcandle.facade;

import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleFetchService;
import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleService;
import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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


    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();

        bithumbCandleFetchService.fetchBithumbData(bithumbSymbols)
                .buffer(1000)
                .flatMap(bithumbCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> bithumbCandleService.saveAll(bithumbCandles))))
                .subscribe();

        log.info("bithumb fetch done");
    }
}
