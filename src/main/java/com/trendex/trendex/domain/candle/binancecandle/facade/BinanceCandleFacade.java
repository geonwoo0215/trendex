package com.trendex.trendex.domain.candle.binancecandle.facade;

import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.candle.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.candle.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<BinanceCandle> binanceCandles = binanceCandleFetchService.fetchBinanceData(binanceSymbols);
        binanceCandleService.saveAll(binanceCandles);
        log.info("binance fetch done");
    }

}
