package com.trendex.trendex.domain.candle.binancecandle.service;

import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.candle.binancecandle.repository.BinanceCandleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceCandleService {

    private final BinanceCandleJdbcRepository binanceCandleJdbcRepository;

    @Transactional
    public void saveAll(List<BinanceCandle> binanceCandles) {
        binanceCandleJdbcRepository.batchInsert(binanceCandles);
    }


}
