package com.trendex.trendex.domain.trade.upbittrade.service;

import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitTradeResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitTradeFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public List<UpbitTrade> fetchUpbitData(List<UpbitSymbol> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getTrades(upbitSymbol.getSymbol())
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitTradeResponse::toUpbitTrade)
                )
                .collectList()
                .block();

    }

}
