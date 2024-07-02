package com.trendex.trendex.domain.candle.coinonecandle.service;

import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import com.trendex.trendex.domain.candle.coinonecandle.repository.CoinoneCandleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneCandleService {

    private final CoinoneCandleJdbcRepository coinoneCandleJdbcRepository;

    @Transactional
    public void saveAll(List<CoinoneCandle> coinoneCandles) {
        coinoneCandleJdbcRepository.batchInsert(coinoneCandles);
    }

}
