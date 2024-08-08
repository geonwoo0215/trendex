package com.trendex.trendex.domain.orderbook.binanceorderbook.facade;

import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.orderbook.binanceorderbook.service.BinanceOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.binanceorderbook.service.BinanceOrderBookService;
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
public class BinanceOrderBookFacade {

    private final BinanceOrderBookService binanceOrderBookService;

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceOrderBookFetchService binanceOrderBookFetchService;

    @Scheduled(cron = "0 */5 * * * *")
    public void fetchAndSaveBinanceData() {
        List<String> binanceSymbols = binanceSymbolService.findAll();
        binanceOrderBookFetchService.fetchBinanceData(binanceSymbols)
                .buffer(1000)
                .flatMap(binanceOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> binanceOrderBookService.saveAll(binanceOrderBooks))))
                .subscribe();
        log.info("binance fetch done");
    }

}
