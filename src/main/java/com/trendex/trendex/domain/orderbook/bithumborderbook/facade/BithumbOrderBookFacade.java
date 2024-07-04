package com.trendex.trendex.domain.orderbook.bithumborderbook.facade;

import com.trendex.trendex.domain.orderbook.bithumborderbook.service.BithumbOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.bithumborderbook.service.BithumbOrderBookService;
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
public class BithumbOrderBookFacade {

    private final BithumbOrderBookService bithumbOrderBookService;

    private final BithumbSymbolService bithumbSymbolService;

    private final BithumbOrderBookFetchService bithumbOrderBookFetchService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();
        bithumbOrderBookFetchService.fetchBithumbData(bithumbSymbols)
                .buffer(1000)
                .flatMap(bithumbOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> bithumbOrderBookService.saveAll(bithumbOrderBooks))))
                .subscribe();
        log.info("bithumb fetch done");
    }

}
