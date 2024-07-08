package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.repository.UpbitCandleJdbcRepository;
import com.trendex.trendex.domain.candle.upbitcandle.repository.UpbitCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitCandleService {

    private final UpbitCandleJdbcRepository upbitCandleJdbcRepository;

    private final UpbitCandleRepository upbitCandleRepository;

    @Transactional
    public void saveAll(List<UpbitCandle> upbitCandles) {
        upbitCandleJdbcRepository.batchInsert(upbitCandles);
    }

    @Transactional(readOnly = true)
    public List<CryptoVolume> getCandlesByMarketAndTime(String market) {
        return upbitCandleRepository.findVolumeBySymbolAndTimeRange(market, LocalDateTime.now().minusMinutes(11L), LocalDateTime.now().minusMinutes(2L));
    }


}
