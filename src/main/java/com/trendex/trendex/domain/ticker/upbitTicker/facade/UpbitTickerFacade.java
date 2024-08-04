package com.trendex.trendex.domain.ticker.upbitTicker.facade;

import com.trendex.trendex.domain.ticker.upbitTicker.model.UpbitTicker;
import com.trendex.trendex.domain.ticker.upbitTicker.service.UpbitTickerFetchService;
import com.trendex.trendex.domain.ticker.upbitTicker.service.UpbitTickerService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitTickerFacade {

    private final UpbitTickerFetchService upbitTickerFetchService;

    private final UpbitTickerService upbitTickerService;

    private final UpbitMarketService upbitMarketService;

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchAndSaveBinanceData() {

        List<String> upbitMarkets = upbitMarketService.findMarketsStartWithKRW();
        Flux<UpbitTicker> upbitTickerFlux = upbitTickerFetchService.fetchUpbitData(upbitMarkets);
        upbitTickerService.saveAll(upbitTickerFlux).subscribe();
    }


}
