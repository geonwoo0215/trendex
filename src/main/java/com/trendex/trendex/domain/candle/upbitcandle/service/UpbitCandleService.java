package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.repository.UpbitCandleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitCandleService {

    private final UpbitCandleJdbcRepository upbitCandleJdbcRepository;

    @Transactional
    public void saveAll(List<UpbitCandle> upbitCandles) {
        upbitCandleJdbcRepository.batchInsert(upbitCandles);
    }

}
