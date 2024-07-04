package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
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
                        upbitWebClientService.getMinuteCandle(3, upbitSymbol.getSymbol(), 20)
                                .flatMapMany(Flux::fromIterable)
                                .map(upbitCandleData -> upbitCandleData.toUpbitCandle())
                )
                .sequential();

    }


}
