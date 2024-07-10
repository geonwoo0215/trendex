package com.trendex.trendex.domain.macd.upbitmacd.service;

import com.trendex.trendex.domain.candle.CandleAnalysisService;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacdSignal;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpBitMacdSignalRepository;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpbitMacdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitMacdService {


    private final UpbitMacdRepository upbitMacdRepository;

    private final UpBitMacdSignalRepository upBitMacdSignalRepository;

    private final CandleAnalysisService candleAnalysisService;

    @Transactional
    public UpbitMacd save(String symbol, Double macd) {
        UpbitMacd upbitMacd = new UpbitMacd(symbol, macd, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        return upbitMacdRepository.save(upbitMacd);
    }

    @Transactional(readOnly = true)
    public List<Double> findAllBySymbolAndTimeStamp(String symbol, long timestamp) {
        return upbitMacdRepository.findMarketBySymbolAndTime(symbol, timestamp)
                .stream()
                .map(UpbitMacd::getValue)
                .toList();

    }


    @Transactional
    public void signalSave(Double signalValue, UpbitMacd upbitMacd) {
        UpbitMacdSignal upbitMacdSignal = new UpbitMacdSignal(signalValue, upbitMacd);
        upBitMacdSignalRepository.save(upbitMacdSignal);
    }


}
