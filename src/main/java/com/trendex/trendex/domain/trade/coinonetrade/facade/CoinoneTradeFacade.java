package com.trendex.trendex.domain.trade.coinonetrade.facade;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import com.trendex.trendex.domain.trade.coinonetrade.service.CoinoneTradeFetchService;
import com.trendex.trendex.domain.trade.coinonetrade.service.CoinoneTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneTradeFacade {

    private final CoinoneTradeService coinoneTradeService;

    private final CoinoneTradeFetchService coinoneTradeFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();

        coinoneTradeFetchService.fetchCoinoneData(coinoneSymbols)
                .buffer(1000)
                .flatMap(coinoneTrades -> Mono.fromFuture(CompletableFuture.runAsync(() -> coinoneTradeService.saveAll(coinoneTrades))))
                .subscribe();
        log.info("coinone fetch done");
    }
}
