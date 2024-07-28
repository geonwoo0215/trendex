package com.trendex.trendex.domain.rsi.binancersi.facade;

import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceRsiFacade {

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceRsiService binanceRsiService;

    public List<RsiResponse> findAllRsis() {
        List<String> binanceSymbols = binanceSymbolService.findAll();
        return binanceRsiService.findLatest(binanceSymbols);
    }

}
