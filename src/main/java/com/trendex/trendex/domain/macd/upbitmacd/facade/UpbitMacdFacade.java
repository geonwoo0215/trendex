package com.trendex.trendex.domain.macd.upbitmacd.facade;

import com.trendex.trendex.domain.macd.dto.MacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import com.trendex.trendex.global.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitMacdFacade {

    private static final String REDIS_KEY = "BINANCE_RSI_RESPONSES";

    private final UpbitMarketService upbitMarketService;

    private final UpbitMacdService upbitMacdService;

    private final RedisUtil redisUtil;

    public List<MacdResponse> findAllMacds() {

        if (redisUtil.hasKey(REDIS_KEY)) {
            return (List<MacdResponse>) redisUtil.get(REDIS_KEY);
        }

        List<String> upbitMarkets = upbitMarketService.findAll();
        List<MacdResponse> macdResponses = upbitMacdService.findLatest(upbitMarkets);

        redisUtil.setData(REDIS_KEY, macdResponses, 60);
        return macdResponses;
    }


}
