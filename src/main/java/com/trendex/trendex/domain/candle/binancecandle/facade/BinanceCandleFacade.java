package com.trendex.trendex.domain.candle.binancecandle.facade;

import com.trendex.trendex.domain.candle.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.candle.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolService;
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
public class BinanceCandleFacade {

    private final BinanceCandleService binanceCandleService;

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceCandleFetchService binanceCandleFetchService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();
        binanceCandleFetchService.fetchBinanceData(binanceSymbols)
                .buffer(1000)
                .flatMap(binanceCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> binanceCandleService.saveAll(binanceCandles))))
                .subscribe();
        log.info("binance fetch done");
    }

}
