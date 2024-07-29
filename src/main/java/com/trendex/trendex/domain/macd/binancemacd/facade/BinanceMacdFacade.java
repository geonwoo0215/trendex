package com.trendex.trendex.domain.macd.binancemacd.facade;

import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.macd.dto.MacdResponse;
import com.trendex.trendex.global.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceMacdFacade {

    private static final String REDIS_KEY = "BINANCE_RSI_RESPONSES";

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceMacdService binanceMacdService;

    private final RedisUtil redisUtil;

    public List<MacdResponse> findAllMacds() {

        if (redisUtil.hasKey(REDIS_KEY)) {
            return (List<MacdResponse>) redisUtil.get(REDIS_KEY);
        }
        
        List<String> binanceSymbols = binanceSymbolService.findAll();
        List<MacdResponse> macdResponses = binanceMacdService.findLatest(binanceSymbols);

        redisUtil.setData(REDIS_KEY, macdResponses, 60);
        return macdResponses;
    }

}
