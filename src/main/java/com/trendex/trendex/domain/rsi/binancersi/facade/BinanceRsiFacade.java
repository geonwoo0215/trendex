package com.trendex.trendex.domain.rsi.binancersi.facade;

import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import com.trendex.trendex.global.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceRsiFacade {

    private static final String REDIS_KEY = "BINANCE_RSI_RESPONSES";
    private final BinanceSymbolService binanceSymbolService;
    private final BinanceRsiService binanceRsiService;
    private final RedisUtil redisUtil;

    public List<RsiResponse> findAllRsis() {

        if (redisUtil.hasKey(REDIS_KEY)) {
            return (List<RsiResponse>) redisUtil.get(REDIS_KEY);
        }

        List<String> binanceSymbols = binanceSymbolService.findAll();
        List<RsiResponse> rsiResponses = binanceRsiService.findLatest(binanceSymbols);

        redisUtil.setData(REDIS_KEY, rsiResponses, 60);

        return rsiResponses;
    }

}
