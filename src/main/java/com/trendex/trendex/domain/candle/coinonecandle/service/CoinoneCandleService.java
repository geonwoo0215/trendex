package com.trendex.trendex.domain.candle.coinonecandle.service;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import com.trendex.trendex.domain.candle.coinonecandle.repository.CoinoneCandleJdbcRepository;
import com.trendex.trendex.domain.candle.coinonecandle.repository.CoinoneCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneCandleService {

    private final CoinoneCandleJdbcRepository coinoneCandleJdbcRepository;

    private final CoinoneCandleRepository coinoneCandleRepository;

    @Transactional
    public void saveAll(List<CoinoneCandle> coinoneCandles) {
        coinoneCandleJdbcRepository.batchInsert(coinoneCandles);
    }

    @Transactional(readOnly = true)
    public List<CryptoVolume> getCandlesBySymbolAndTime(String symbol) {

        long startTimestamp = LocalDateTime.now().minusMinutes(11L).toEpochSecond(ZoneOffset.UTC) * 1000;
        long endTimestamp = LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000;

        return coinoneCandleRepository.findBySymbolAndTimestampBetweenOrderByTimestampAsc(symbol, startTimestamp, endTimestamp);
    }

}
