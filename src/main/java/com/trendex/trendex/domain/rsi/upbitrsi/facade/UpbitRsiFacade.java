package com.trendex.trendex.domain.rsi.upbitrsi.facade;

import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.service.UpbitRsiService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitRsiFacade {

    private final UpbitMarketService upbitMarketService;

    private final UpbitRsiService upbitRsiService;

    public List<RsiResponse> findAllRsis() {
        List<String> upbitMarkets = upbitMarketService.findAll();
        return upbitRsiService.findLatest(upbitMarkets);
    }


}
