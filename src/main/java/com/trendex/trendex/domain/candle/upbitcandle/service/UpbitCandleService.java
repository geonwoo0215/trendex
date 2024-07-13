package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.repository.UpbitCandleJdbcRepository;
import com.trendex.trendex.domain.candle.upbitcandle.repository.UpbitCandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpbitCandleService {

    private final UpbitCandleJdbcRepository upbitCandleJdbcRepository;

    private final UpbitCandleRepository upbitCandleRepository;

    @Transactional
    public Flux<Void> saveAll(Flux<UpbitCandle> upbitCandlesFlux) {
        return upbitCandlesFlux
                .buffer(100)
                .flatMap(upbitCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitCandleJdbcRepository.batchInsert(upbitCandles))));
    }

    @Transactional(readOnly = true)
    public List<Double> getVolumesByMarketAndTime(String market) {
        return upbitCandleRepository.findVolumeByMarketAndTimeRange(market, CandleAnalysisTime.Volume_START_TIME_STAMP.getTime(), CandleAnalysisTime.Volume_END_TIME_STAMP.getTime())
                .stream()
                .map(cryptoVolume -> Double.parseDouble(cryptoVolume.getVolume()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Double> getClosePricesByMarketAndTime(String market, long timestamp) {
        return upbitCandleRepository.findClosePriceByMarketAndTime(market, timestamp)
                .stream()
                .map(CryptoClosePrice::getTradePrice)
                .collect(Collectors.toList());
    }

}
