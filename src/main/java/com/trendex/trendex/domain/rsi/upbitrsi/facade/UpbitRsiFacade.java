package com.trendex.trendex.domain.rsi.upbitrsi.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.rsi.upbitrsi.dto.UpbitRsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.rsi.upbitrsi.service.UpbitRsiService;
import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitRsiFacade {

    private final UpbitMarketService upbitMarketService;

    private final UpbitRsiService upbitRsiService;

    public List<UpbitRsiResponse> findAllRsis() {
        List<String> upbitMarkets = upbitMarketService.findAll()
                .stream()
                .map(UpbitMarket::getMarket)
                .collect(Collectors.toList());

        return upbitRsiService.findLatest(upbitMarkets)
                .stream()
                .map(upbitRsi -> {
                    Decision decision = decideByRsi(upbitRsi);
                    return new UpbitRsiResponse(upbitRsi.getMarket(), decision.getText(), upbitRsi.getValue());
                })
                .collect(Collectors.toList());
    }

    public Decision decideByRsi(UpbitRsi upbitRsi) {
        return CandleAnalysisUtil.decideByRsi(upbitRsi.getValue());
    }


}
