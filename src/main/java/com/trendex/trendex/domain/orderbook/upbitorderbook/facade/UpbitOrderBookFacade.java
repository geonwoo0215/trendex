package com.trendex.trendex.domain.orderbook.upbitorderbook.facade;

import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookService;
import com.trendex.trendex.domain.symbol.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.symbol.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UpbitMarketService upbitMarketService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitMarket> upbitSymbols = upbitMarketService.findAll();
        upbitOrderBookFetchService.fetchUpbitData(upbitSymbols)
                .buffer(1000)
                .flatMap(upbitOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitOrderBookService.saveAll(upbitOrderBooks))))
                .subscribe();
        log.info("upbit fetch done");
    }

}
