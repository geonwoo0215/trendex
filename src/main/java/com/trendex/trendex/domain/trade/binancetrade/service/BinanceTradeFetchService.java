package com.trendex.trendex.domain.trade.binancetrade.service;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceTradeFetchService {

    private final BinanceWebClientService binanceWebClientService;

    public List<BinanceTrade> fetchBinanceData(List<BinanceSymbol> binanceSymbols) {

        return Flux.fromIterable(binanceSymbols)
                .flatMap(binanceSymbol ->
                        binanceWebClientService.getTrade(binanceSymbol.getSymbol())
                                .flatMapMany(Flux::fromIterable)
                                .map(binanceTradeResponse -> binanceTradeResponse.toBinanceTrade(binanceSymbol.getSymbol()))
                )
                .collectList()
                .block();

    }
}
