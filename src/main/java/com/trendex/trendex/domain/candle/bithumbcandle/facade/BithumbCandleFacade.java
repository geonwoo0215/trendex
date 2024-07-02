package com.trendex.trendex.domain.candle.bithumbcandle.facade;

import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleFetchService;
import com.trendex.trendex.domain.candle.bithumbcandle.service.BithumbCandleService;
import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BithumbCandleFacade {

    private final BithumbCandleService bithumbCandleService;

    private final BithumbSymbolService bithumbSymbolService;

    private final BithumbCandleFetchService bithumbCandleFetchService;


    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();
        List<BithumbCandle> bithumbCandles = bithumbCandleFetchService.fetchBithumbData(bithumbSymbols);
        bithumbCandleService.saveAll(bithumbCandles);
        log.info("bithumb fetch done");
    }
}
