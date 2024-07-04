package com.trendex.trendex.domain.trade.binancetrade.facade;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.trade.binancetrade.service.BinanceTradeFetchService;
import com.trendex.trendex.domain.trade.binancetrade.service.BinanceTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceTradeFacade {

    private final BinanceTradeService binanceTradeService;

    private final BinanceTradeFetchService binanceTradeFetchService;

    private final BinanceSymbolService binanceSymbolService;

    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();
        binanceTradeFetchService.fetchBinanceData(binanceSymbols)
                .buffer(1000)
                .flatMap(binanceTrades -> Mono.fromFuture(CompletableFuture.runAsync(() -> binanceTradeService.saveAll(binanceTrades))))
                .subscribe();
        log.info("binance fetch done");
    }

}
