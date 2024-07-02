package com.trendex.trendex.domain.candle.bithumbcandle.service;

import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.candle.bithumbcandle.repository.BithumbCandleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbCandleService {

    private final BithumbCandleJdbcRepository bithumbCandleJdbcRepository;

    @Transactional
    public void saveAll(List<BithumbCandle> bithumbCandles) {
        bithumbCandleJdbcRepository.batchInsert(bithumbCandles);
    }
}
