package com.trendex.trendex.domain.ticker.upbitTicker.service;

import com.trendex.trendex.domain.ticker.upbitTicker.model.UpbitTicker;
import com.trendex.trendex.domain.ticker.upbitTicker.repository.UpbitTickerJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UpbitTickerService {

    private final UpbitTickerJdbcRepository upbitTickerJdbcRepository;

    @Transactional
    public Flux<Void> saveAll(Flux<UpbitTicker> upbitTickerFlux) {
        return upbitTickerFlux
                .buffer(100)
                .flatMap(upbitTickers -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitTickerJdbcRepository.batchInsert(upbitTickers))));
    }


}
