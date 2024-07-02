package com.trendex.trendex.domain.candle.bithumbcandle.service;

import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbCandleFetchService {

    private final BithumbWebClientService bithumbWebClientService;

    public List<BithumbCandle> fetchBithumbData(List<BithumbSymbol> bithumbSymbols) {

        return Flux.fromIterable(bithumbSymbols)
                .flatMap(bithumbSymbol ->
                        bithumbWebClientService.getCandle(bithumbSymbol.getSymbol(), "KRW", "3m")
                                .flatMapMany(candle -> Flux.fromIterable(candle.toCryptoCandleList(bithumbSymbol.getSymbol()))))
                .collectList()
                .block();

    }

}
