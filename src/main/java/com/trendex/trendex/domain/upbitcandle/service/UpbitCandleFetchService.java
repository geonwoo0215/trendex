package com.trendex.trendex.domain.upbitcandle.service;

import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitAccountResponse;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitCandleResponse;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitOrderResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCandleFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitCandle> fetchUpbitData(List<String> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getMinuteCandle(1, upbitSymbol, 1)
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitCandleResponse::toUpbitCandle)
                )
                .sequential();

    }

    public Mono<List<UpbitAccountResponse>> fetchAccounts() {
        return upbitWebClientService.getAccounts();
    }

    public UpbitOrderResponse trade(String market, String side, String volume, String price) {

        return upbitWebClientService.getOrders(market, side, volume, price, "best", "ioc").block();


    }


}
