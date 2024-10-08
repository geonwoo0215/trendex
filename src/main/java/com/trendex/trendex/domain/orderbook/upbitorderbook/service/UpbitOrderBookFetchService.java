package com.trendex.trendex.domain.orderbook.upbitorderbook.service;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitOrderBookResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitOrderBookFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitOrderBook> fetchUpbitData(List<String> upbitMarkets) {
        return Flux.fromIterable(upbitMarkets)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitMarket ->
                        upbitWebClientService.getOrderBook(upbitMarket)
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitOrderBookResponse::toUpbitOrderBook)
                )
                .sequential();
    }

}
