package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitCandleResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitCandleFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitCandle> fetchUpbitData(List<UpbitSymbol> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getMinuteCandle(1, upbitSymbol.getSymbol(), 1)
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitCandleResponse::toUpbitCandle)
                )
                .sequential();

    }


}
