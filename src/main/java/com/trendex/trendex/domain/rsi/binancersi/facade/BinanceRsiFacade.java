package com.trendex.trendex.domain.rsi.binancersi.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceRsiFacade {

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceRsiService binanceRsiService;

    public List<RsiResponse> findAllRsis() {
        List<String> upbitMarkets = binanceSymbolService.findAll()
                .stream()
                .map(BinanceSymbol::getSymbol)
                .collect(Collectors.toList());

        return binanceRsiService.findLatest(upbitMarkets)
                .stream()
                .map(binanceRsi -> {
                    Decision decision = decideByRsi(binanceRsi);
                    return new RsiResponse(binanceRsi.getSymbol(), decision.getText(), binanceRsi.getValue());
                })
                .collect(Collectors.toList());
    }

    public Decision decideByRsi(BinanceRsi binanceRsi) {
        return CandleAnalysisUtil.decideByRsi(binanceRsi.getValue());
    }


}
