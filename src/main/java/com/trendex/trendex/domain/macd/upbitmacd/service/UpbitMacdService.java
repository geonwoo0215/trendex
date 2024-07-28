package com.trendex.trendex.domain.macd.upbitmacd.service;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.macd.dto.MacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpbitMacdJdbcRepository;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpbitMacdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpbitMacdService {


    private final UpbitMacdRepository upbitMacdRepository;

    private final UpbitMacdJdbcRepository upbitMacdJdbcRepository;

    @Transactional
    public void save(String market, Double macdValue, Double macdSignalValue) {
        UpbitMacd upbitMacd = new UpbitMacd(market, macdValue, macdSignalValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        upbitMacdRepository.save(upbitMacd);
    }

    @Transactional(readOnly = true)
    public List<Double> findAllBySymbolAndTimeStamp(String symbol, long timestamp) {
        return upbitMacdRepository.findMacdByMarketAndTime(symbol, timestamp)
                .stream()
                .map(UpbitMacd::getMacdValue)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<MacdResponse> findLatest(List<String> markets) {
        return upbitMacdRepository.findLatestForMarkets(markets)
                .stream()
                .map(upbitMacd -> {
                    Decision decision = CandleAnalysisUtil.decideByMacd(upbitMacd.getMacdValue(), upbitMacd.getMacdSignalValue(), upbitMacd.isSignalHigherThanMacd());
                    return new MacdResponse(upbitMacd.getMarket(), decision.getText(), upbitMacd.getMacdValue(), upbitMacd.getMacdSignalValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(List<UpbitMacd> upbitMacds) {
        upbitMacdJdbcRepository.batchInsert(upbitMacds);
    }

}
