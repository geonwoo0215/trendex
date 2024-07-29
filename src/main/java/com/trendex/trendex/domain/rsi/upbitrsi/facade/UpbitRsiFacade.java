package com.trendex.trendex.domain.rsi.upbitrsi.facade;

import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.service.UpbitRsiService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import com.trendex.trendex.global.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitRsiFacade {

    private static final String REDIS_KEY = "UPBIT_RSI_RESPONSES";

    private final UpbitMarketService upbitMarketService;

    private final UpbitRsiService upbitRsiService;

    private final RedisUtil redisUtil;

    public List<RsiResponse> findAllRsis() {

        if (redisUtil.hasKey(REDIS_KEY)) {
            return (List<RsiResponse>) redisUtil.get(REDIS_KEY);
        }

        List<String> binanceSymbols = upbitMarketService.findAll();
        List<RsiResponse> rsiResponses = upbitRsiService.findLatest(binanceSymbols);

        redisUtil.setData(REDIS_KEY, rsiResponses, 60);

        return rsiResponses;
    }


}
