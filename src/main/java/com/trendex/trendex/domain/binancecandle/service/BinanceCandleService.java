package com.trendex.trendex.domain.binancecandle.service;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.repository.BinanceCandleJdbcRepository;
import com.trendex.trendex.domain.binancecandle.repository.BinanceCandleRepository;
import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
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
public class BinanceCandleService {

    private final BinanceCandleJdbcRepository binanceCandleJdbcRepository;

    private final BinanceCandleRepository binanceCandleRepository;

    @Transactional
    public Flux<Void> saveAll(Flux<BinanceCandle> binanceCandlesFlux) {
        return binanceCandlesFlux
                .buffer(100)
                .flatMap(binanceCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> binanceCandleJdbcRepository.batchInsert(binanceCandles))));
    }

    @Transactional(readOnly = true)
    public List<Double> getCandlesBySymbolAndTime(String symbol) {
        return binanceCandleRepository.findVolumeBySymbolAndTimeRange(symbol, CandleAnalysisTime.Volume_START_TIME_STAMP.getTime(), CandleAnalysisTime.Volume_END_TIME_STAMP.getTime())
                .stream()
                .map(cryptoVolume -> Double.parseDouble(cryptoVolume.getVolume()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Double> getClosePricesBySymbolAndTime(String market, long timestamp) {
        return binanceCandleRepository.findTradePriceBySymbolAndTime(market, timestamp)
                .stream()
                .map(CryptoClosePrice::getTradePrice)
                .collect(Collectors.toList());
    }

}
