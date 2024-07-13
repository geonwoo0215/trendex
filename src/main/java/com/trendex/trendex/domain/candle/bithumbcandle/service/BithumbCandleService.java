package com.trendex.trendex.domain.candle.bithumbcandle.service;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.candle.bithumbcandle.repository.BithumbCandleJdbcRepository;
import com.trendex.trendex.domain.candle.bithumbcandle.repository.BithumbCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return bithumbCandleRepository.findVolumeBySymbolAndTimeRange(symbol, CandleAnalysisTime.Volume_START_TIME_STAMP.getTime(), CandleAnalysisTime.Volume_START_TIME_STAMP.getTime());
    }

}
