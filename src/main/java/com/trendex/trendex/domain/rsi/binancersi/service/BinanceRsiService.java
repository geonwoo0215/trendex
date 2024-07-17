package com.trendex.trendex.domain.rsi.binancersi.service;

import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.repository.BinanceRsiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceRsiService {

    private final BinanceRsiRepository binanceRsiRepository;

    @Transactional
    public void save(String symbol, Double value) {
        BinanceRsi binanceRsi = new BinanceRsi(symbol, value, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        binanceRsiRepository.save(binanceRsi);
    }


    @Transactional(readOnly = true)
    public List<BinanceRsi> findLatest(List<String> markets) {
        return binanceRsiRepository.findLatestForSymbols(markets);
    }
}
