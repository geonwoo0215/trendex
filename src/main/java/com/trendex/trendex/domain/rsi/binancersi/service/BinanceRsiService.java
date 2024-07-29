package com.trendex.trendex.domain.rsi.binancersi.service;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.repository.BinanceRsiJdbcRepository;
import com.trendex.trendex.domain.rsi.binancersi.repository.BinanceRsiRepository;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceRsiService {

    private final BinanceRsiRepository binanceRsiRepository;

    private final BinanceRsiJdbcRepository binanceRsiJdbcRepository;

    @Transactional
    public void save(String symbol, Double value) {
        BinanceRsi binanceRsi = new BinanceRsi(symbol, value, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        binanceRsiRepository.save(binanceRsi);
    }


    @Transactional(readOnly = true)
    public List<RsiResponse> findLatest(List<String> markets) {
        return binanceRsiRepository.findLatestForSymbols(markets)
                .stream()
                .map(binanceRsi -> {
                    Decision decision = CandleAnalysisUtil.decideByRsi(binanceRsi.getValue());
                    return new RsiResponse(binanceRsi.getSymbol(), decision.getText(), binanceRsi.getValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(List<BinanceRsi> binanceRsis) {
        binanceRsiJdbcRepository.batchInsert(binanceRsis);
    }
}
