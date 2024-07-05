package com.trendex.trendex.domain.candle.binancecandle.service;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.candle.binancecandle.repository.BinanceCandleJdbcRepository;
import com.trendex.trendex.domain.candle.binancecandle.repository.BinanceCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceCandleService {

    private final BinanceCandleJdbcRepository binanceCandleJdbcRepository;

    private final BinanceCandleRepository binanceCandleRepository;

    @Transactional
    public void saveAll(List<BinanceCandle> binanceCandles) {
        binanceCandleJdbcRepository.batchInsert(binanceCandles);
    }

    @Transactional(readOnly = true)
    public List<CryptoVolume> getCandlesBySymbolAndTime(String symbol) {

        long startTimestamp = LocalDateTime.now().minusMinutes(11L).toEpochSecond(ZoneOffset.UTC) * 1000;
        long endTimestamp = LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000;

        return binanceCandleRepository.findBySymbolAndTimestampBetweenOrderByTimestampAsc(symbol, startTimestamp, endTimestamp);
    }

}
