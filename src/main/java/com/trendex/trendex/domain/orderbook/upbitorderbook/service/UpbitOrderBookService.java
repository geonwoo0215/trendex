package com.trendex.trendex.domain.orderbook.upbitorderbook.service;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.repository.UpbitOrderBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UpbitOrderBookService {

    private final UpbitOrderBookRepository upbitOrderBookRepository;

    @Transactional
    public Flux<Void> saveAll(Flux<UpbitOrderBook> upbitOrderBookFlux) {
        return upbitOrderBookFlux
                .buffer(100)
                .flatMap(upbitOrderBooks -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitOrderBookRepository.saveAll(upbitOrderBooks))));
    }

}
