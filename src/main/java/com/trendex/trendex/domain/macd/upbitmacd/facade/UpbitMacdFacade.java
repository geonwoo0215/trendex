package com.trendex.trendex.domain.macd.upbitmacd.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.macd.upbitmacd.dto.UpbitMacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.symbol.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.symbol.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitMacdFacade {

    private final UpbitMarketService upbitMarketService;

    private final UpbitMacdService upbitMacdService;

    public List<UpbitMacdResponse> findAllMacds() {
        List<String> upbitMarkets = upbitMarketService.findAll()
                .stream()
                .map(UpbitMarket::getMarket)
                .collect(Collectors.toList());

        return upbitMacdService.findLatest(upbitMarkets)
                .stream()
                .map(upbitMacd -> {
                    Decision decision = decideByMacd(upbitMacd);
                    return new UpbitMacdResponse(upbitMacd.getMarket(), decision.getText(), upbitMacd.getMacdValue(), upbitMacd.getMacdSignalValue());
                })
                .collect(Collectors.toList());
    }

    public Decision decideByMacd(UpbitMacd upbitMacd) {
        return CandleAnalysisUtil.decideByMacd(upbitMacd.getMacdValue(), upbitMacd.getMacdSignalValue(), upbitMacd.isSignalHigherThanMacd());
    }
}
