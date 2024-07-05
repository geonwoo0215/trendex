package com.trendex.trendex.domain.candle.bithumbcandle.service;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.candle.bithumbcandle.repository.BithumbCandleJdbcRepository;
import com.trendex.trendex.domain.candle.bithumbcandle.repository.BithumbCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbCandleService {

    private final BithumbCandleJdbcRepository bithumbCandleJdbcRepository;

    private final BithumbCandleRepository bithumbCandleRepository;

    @Transactional
    public void saveAll(List<BithumbCandle> bithumbCandles) {
        bithumbCandleJdbcRepository.batchInsert(bithumbCandles);
    }

    @Transactional(readOnly = true)
    public List<CryptoVolume> getCandlesBySymbolAndTime(String symbol) {

        long startTimestamp = LocalDateTime.now().minusMinutes(11L).toEpochSecond(ZoneOffset.UTC) * 1000;
        long endTimestamp = LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000;

        return bithumbCandleRepository.findBySymbolAndTimestampBetweenOrderByTimestampAsc(symbol, startTimestamp, endTimestamp);
    }

}
