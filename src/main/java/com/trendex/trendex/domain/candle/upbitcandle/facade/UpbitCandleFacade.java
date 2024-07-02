package com.trendex.trendex.domain.candle.upbitcandle.facade;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCandleFacade {

    private final UpbitCandleService upbitCandleService;

    private final UpbitCandleFetchService upbitCandleFetchService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        List<UpbitCandle> upbitCandles = upbitCandleFetchService.fetchUpbitData(upbitSymbols);
        upbitCandleService.saveAll(upbitCandles);
        log.info("upbit fetch done");
    }

}
