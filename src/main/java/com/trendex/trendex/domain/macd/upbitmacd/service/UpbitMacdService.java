package com.trendex.trendex.domain.macd.upbitmacd.service;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
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
    public List<UpbitMacd> findLatest(List<String> markets) {
        return upbitMacdRepository.findLatestForMarkets(markets);
    }

}
