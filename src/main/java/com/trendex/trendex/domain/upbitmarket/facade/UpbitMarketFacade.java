package com.trendex.trendex.domain.upbitmarket.facade;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketFetchService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitMarketFacade {

    private final UpbitMarketFetchService upbitSymbolFetchService;

    private final UpbitMarketService upbitMarketService;

    @Scheduled(fixedRate = 100000)
    public void fetchAndSaveUpbitData() {
        List<UpbitMarket> upbitMarkets = upbitSymbolFetchService.fetchUpbitData();
        upbitMarketService.saveAll(upbitMarkets);
    }
}
