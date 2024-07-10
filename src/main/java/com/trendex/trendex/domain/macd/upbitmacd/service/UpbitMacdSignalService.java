package com.trendex.trendex.domain.macd.upbitmacd.service;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacdSignal;
import com.trendex.trendex.domain.macd.upbitmacd.repository.UpBitMacdSignalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpbitMacdSignalService {

    private final UpBitMacdSignalRepository upBitMacdSignalRepository;

    @Transactional(readOnly = true)
    public boolean findLatestSignalIsHigherThanMacd() {
        return upBitMacdSignalRepository.findLatest().stream()
                .findFirst()
                .map(UpbitMacdSignal::isHigherThanMacd)
                .orElseThrow(() -> new RuntimeException("No signals found"));
    }
}
