package com.trendex.trendex.domain.rsi.upbitrsi.service;

import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.rsi.upbitrsi.repository.UpbitRsiJdbcRepository;
import com.trendex.trendex.domain.rsi.upbitrsi.repository.UpbitRsiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpbitRsiService {

    private final UpbitRsiRepository upbitRsiRepository;

    private final UpbitRsiJdbcRepository upbitRsiJdbcRepository;

    @Transactional
    public void save(String market, Double value) {
        UpbitRsi upbitRsi = new UpbitRsi(market, value, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        upbitRsiRepository.save(upbitRsi);
    }

    @Transactional(readOnly = true)
    public UpbitRsi findLatest(String market) {
        return upbitRsiRepository.findLatest(market)
                .orElseThrow(() -> new RuntimeException());

    }

    @Transactional(readOnly = true)
    public List<RsiResponse> findLatest(List<String> markets) {
        return upbitRsiRepository.findLatestForMarkets(markets)
                .stream()
                .map(upbitRsi -> {
                    Decision decision = CandleAnalysisUtil.decideByRsi(upbitRsi.getValue());
                    return new RsiResponse(upbitRsi.getMarket(), decision.getText(), upbitRsi.getValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(List<UpbitRsi> upbitRsis) {
        upbitRsiJdbcRepository.batchInsert(upbitRsis);
    }


}
