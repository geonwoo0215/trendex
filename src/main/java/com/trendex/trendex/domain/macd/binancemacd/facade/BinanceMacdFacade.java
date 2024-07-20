package com.trendex.trendex.domain.macd.binancemacd.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.macd.dto.MacdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceMacdFacade {

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceMacdService binanceMacdService;

    public List<MacdResponse> findAllMacds() {
        List<String> binanceMarkets = binanceSymbolService.findAll()
                .stream()
                .map(BinanceSymbol::getSymbol)
                .collect(Collectors.toList());

        return binanceMacdService.findLatest(binanceMarkets)
                .stream()
                .map(binanceMacd -> {
                    Decision decision = decideByMacd(binanceMacd);
                    return new MacdResponse(binanceMacd.getSymbol(), decision.getText(), binanceMacd.getMacdValue(), binanceMacd.getMacdSignalValue());
                })
                .collect(Collectors.toList());
    }

    public Decision decideByMacd(BinanceMacd binanceMacd) {
        return CandleAnalysisUtil.decideByMacd(binanceMacd.getMacdValue(), binanceMacd.getMacdSignalValue(), binanceMacd.getSignalHigherThanMacd());
    }

}
