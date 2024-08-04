package com.trendex.trendex.domain.ticker.upbitTicker.service;

import com.trendex.trendex.domain.ticker.upbitTicker.model.UpbitTicker;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitTickerResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitTickerFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitTicker> fetchUpbitData(List<String> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitMarket ->
                        upbitWebClientService.getTicker(upbitMarket)
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitTickerResponse::toUpbitTicker))
                .sequential();

    }


}
