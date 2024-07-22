package com.trendex.trendex.domain.binancecandle.service;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.repository.BinanceCandleJdbcRepository;
import com.trendex.trendex.domain.binancecandle.repository.BinanceCandleRepository;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
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
    public List<CryptoClosePrice> getClosePricesBySymbolAndTime(String symbol, long timestamp) {
        return binanceCandleRepository.findTradePriceBySymbolAndTime(symbol, timestamp);
    }

}
