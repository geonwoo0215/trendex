package com.trendex.trendex.domain.cryptocandle.service;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.cryptocandle.repository.CryptoCandleJdbcRepository;
import com.trendex.trendex.domain.cryptocandle.repository.CryptoCandleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCandleService {

    private final CryptoCandleRepository cryptoCandleRepository;

    private final CryptoCandleJdbcRepository cryptoCandleJdbcRepository;

    @Transactional
    public void saveAll(List<CryptoCandle> cryptoCandles) {
        cryptoCandleJdbcRepository.batchInsert(cryptoCandles);
    }
}
