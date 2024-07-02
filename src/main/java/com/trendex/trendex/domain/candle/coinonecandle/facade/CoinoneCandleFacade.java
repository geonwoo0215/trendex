package com.trendex.trendex.domain.candle.coinonecandle.facade;

import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import com.trendex.trendex.domain.candle.coinonecandle.service.CoinoneCandleFetchService;
import com.trendex.trendex.domain.candle.coinonecandle.service.CoinoneCandleService;
import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneCandleFacade {

    private final CoinoneCandleService coinoneCandleService;

    private final CoinoneCandleFetchService coinoneCandleFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();
        List<CoinoneCandle> coinoneCandles = coinoneCandleFetchService.fetchCoinoneData(coinoneSymbols);
        coinoneCandleService.saveAll(coinoneCandles);
        log.info("coinone fetch done");
    }

}
