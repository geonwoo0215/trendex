package com.trendex.trendex.domain.candle.upbitcandle.facade;

import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
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
public class UpbitCandleFacade {

    private final UpbitCandleService upbitCandleService;

    private final UpbitCandleFetchService upbitCandleFetchService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        upbitCandleFetchService.fetchUpbitData(upbitSymbols)
                .buffer(1000)
                .flatMap(upbitCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitCandleService.saveAll(upbitCandles))))
                .subscribe();
        log.info("upbit fetch done");
    }

}
