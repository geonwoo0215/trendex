package com.trendex.trendex.domain.candle.binancecandle.service;

import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.global.client.webclient.dto.binance.BinanceCandleResponse;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceCandleFetchService {

    private final BinanceWebClientService binanceWebClientService;

    public Flux<BinanceCandle> fetchBinanceData(List<BinanceSymbol> binanceSymbols) {

        return Flux.fromIterable(binanceSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(binanceSymbol ->
                        binanceWebClientService.getCandle(binanceSymbol.getSymbol(), "3m", 20)
                                .flatMapMany(Flux::fromIterable)
                                .map(binanceCandle -> mapToBinanceCandle(binanceCandle).toBinanceCandle(binanceSymbol.getSymbol()))
                )
                .sequential();

    }

    private BinanceCandleResponse mapToBinanceCandle(List<Object> candleData) {
        long klineOpenTime = (long) candleData.get(0);
        String openPrice = (String) candleData.get(1);
        String highPrice = (String) candleData.get(2);
        String lowPrice = (String) candleData.get(3);
        String closePrice = (String) candleData.get(4);
        String volume = (String) candleData.get(5);
        long klineCloseTime = (long) candleData.get(6);
        String quoteAssetVolume = (String) candleData.get(7);
        int numberOfTrades = (int) candleData.get(8);
        String takerBuyBaseAssetVolume = (String) candleData.get(9);
        String takerBuyQuoteAssetVolume = (String) candleData.get(10);

        return new BinanceCandleResponse(
                klineOpenTime, openPrice, highPrice, lowPrice, closePrice,
                volume, klineCloseTime, quoteAssetVolume, numberOfTrades,
                takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume
        );
    }
}
