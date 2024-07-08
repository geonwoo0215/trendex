package com.trendex.trendex.domain.trade.upbittrade.facade;

import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
import com.trendex.trendex.domain.trade.upbittrade.service.UpbitTradeFetchService;
import com.trendex.trendex.domain.trade.upbittrade.service.UpbitTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitTradeFacade {

    private final UpbitTradeService upbitTradeService;

    private final UpbitTradeFetchService upbitTradeFetchService;

    private final UpbitSymbolService upbitSymbolService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();

        upbitTradeFetchService.fetchUpbitData(upbitSymbols)
                .buffer(1000)
                .flatMap(upbitTrades -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitTradeService.saveAll(upbitTrades))))
                .subscribe();
        log.info("upbit fetch done");
    }

}
