package com.trendex.trendex.domain.macd.binancemacd.facade;

import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.macd.dto.MacdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceMacdFacade {

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceMacdService binanceMacdService;

    public List<MacdResponse> findAllMacds() {
        List<String> binanceSymbols = binanceSymbolService.findAll();
        return binanceMacdService.findLatest(binanceSymbols);
    }

}
