package com.trendex.trendex.domain.macd.upbitmacd.facade;

import com.trendex.trendex.domain.macd.dto.MacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitMacdFacade {

    private final UpbitMarketService upbitMarketService;

    private final UpbitMacdService upbitMacdService;

    public List<MacdResponse> findAllMacds() {
        List<String> upbitMarkets = upbitMarketService.findAll();
        return upbitMacdService.findLatest(upbitMarkets);
    }


}
