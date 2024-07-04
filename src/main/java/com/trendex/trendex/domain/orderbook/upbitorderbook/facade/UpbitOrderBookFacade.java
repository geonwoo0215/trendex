package com.trendex.trendex.domain.orderbook.upbitorderbook.facade;

import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookService;
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
public class UpbitOrderBookFacade {

    private final UpbitOrderBookService upbitOrderBookService;

    private final UpbitOrderBookFetchService upbitOrderBookFetchService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        upbitOrderBookFetchService.fetchUpbitData(upbitSymbols)
                .buffer(1000)
                .flatMap(upbitOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitOrderBookService.saveAll(upbitOrderBooks))))
                .subscribe();
        log.info("upbit fetch done");
    }

}
