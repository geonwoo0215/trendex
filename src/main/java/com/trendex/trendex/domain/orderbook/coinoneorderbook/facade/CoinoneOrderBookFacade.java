package com.trendex.trendex.domain.orderbook.coinoneorderbook.facade;

import com.trendex.trendex.domain.orderbook.coinoneorderbook.service.CoinoneOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.coinoneorderbook.service.CoinoneOrderBookService;
import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneOrderBookFacade {

    private final CoinoneOrderBookService coinoneOrderBookService;

    private final CoinoneOrderBookFetchService coinoneOrderBookFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();
        coinoneOrderBookFetchService.fetchCoinoneData(coinoneSymbols)
                .buffer(1000)
                .flatMap(coinoneOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> coinoneOrderBookService.saveAll(coinoneOrderBooks))))
                .subscribe();

        log.info("coinone fetch done");
    }

}
