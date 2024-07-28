package com.trendex.trendex.domain.macd.binancemacd.service;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import com.trendex.trendex.domain.macd.binancemacd.repository.BinanceMacdJdbcRepository;
import com.trendex.trendex.domain.macd.binancemacd.repository.BinanceMacdRepository;
import com.trendex.trendex.domain.macd.dto.MacdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceMacdService {

    private final BinanceMacdRepository binanceMacdRepository;

    private final BinanceMacdJdbcRepository binanceMacdJdbcRepository;

    @Transactional
    public void save(String market, Double macdValue, Double macdSignalValue) {
        BinanceMacd binanceMacd = new BinanceMacd(market, macdValue, macdSignalValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        binanceMacdRepository.save(binanceMacd);
    }

    @Transactional(readOnly = true)
    public List<Double> findAllBySymbolAndTimeStamp(String symbol, long timestamp) {
        return binanceMacdRepository.findMacdBySymbolAndTime(symbol, timestamp)
                .stream()
                .map(BinanceMacd::getMacdValue)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<MacdResponse> findLatest(List<String> markets) {
        return binanceMacdRepository.findLatestForSymbol(markets)
                .stream()
                .map(binanceMacd -> {
                    Decision decision = CandleAnalysisUtil.decideByMacd(binanceMacd.getMacdValue(), binanceMacd.getMacdSignalValue(), binanceMacd.getSignalHigherThanMacd());
                    return new MacdResponse(binanceMacd.getSymbol(), decision.getText(), binanceMacd.getMacdValue(), binanceMacd.getMacdSignalValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(List<BinanceMacd> binanceMacds) {
        binanceMacdJdbcRepository.batchInsert(binanceMacds);
    }

}
