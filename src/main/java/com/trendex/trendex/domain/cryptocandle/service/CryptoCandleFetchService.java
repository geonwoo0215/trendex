package com.trendex.trendex.domain.cryptocandle.service;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.global.client.webclient.dto.binance.BinanceCandle;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoCandleFetchService {

    private final BinanceWebClientService binanceWebClientService;

    private final BithumbWebClientService bithumbWebClientService;

    private final CoinoneWebClientService coinoneWebClientService;

    private final UpbitWebClientService upbitWebClientService;

    public List<CryptoCandle> fetchBinanceData(List<BinanceSymbol> binanceSymbols) {

        return Flux.fromIterable(binanceSymbols)
                .flatMap(binanceSymbol ->
                        binanceWebClientService.getCandle(binanceSymbol.getSymbol(), "3m", 20)
                                .flatMapMany(Flux::fromIterable)
                                .map(binanceCandle -> mapToBinanceCandle(binanceCandle).toCryptoCandle(binanceSymbol.getSymbol()))
                )
                .collectList()
                .block();

    }


    public List<CryptoCandle> fetchBithumbData(List<BithumbSymbol> bithumbSymbols) {

        return Flux.fromIterable(bithumbSymbols)
                .flatMap(bithumbSymbol ->
                        bithumbWebClientService.getCandle(bithumbSymbol.getSymbol(), "KRW", "3m")
                                .flatMapMany(candle -> Flux.fromIterable(candle.toCryptoCandleList(bithumbSymbol.getSymbol()))))
                .collectList()
                .block();

    }

    public List<CryptoCandle> fetchCoinoneData(List<CoinoneSymbol> coinoneSymbols) {

        return Flux.fromIterable(coinoneSymbols)
                .flatMap(coinoneSymbol ->
                        coinoneWebClientService.getCandle("KRW", coinoneSymbol.getSymbol(), "3m", 20)
                                .flatMapMany(coinoneCandle ->
                                        Optional.ofNullable(coinoneCandle.getChart())
                                                .map(chart -> Flux.fromIterable(chart)
                                                        .map(chartData -> chartData.toCryptoCandle(coinoneSymbol.getSymbol()))
                                                )
                                                .orElseGet(Flux::empty)
                                )
                )
                .collectList()
                .block();

    }

    public List<CryptoCandle> fetchUpbitData(List<UpbitSymbol> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getMinuteCandle(3, upbitSymbol.getSymbol(), 20)
                                .flatMapMany(Flux::fromIterable)
                                .map(upbitCandleData -> upbitCandleData.toCryptoCandle(upbitSymbol.getSymbol()))
                )
                .collectList()
                .block();

    }

    private BinanceCandle mapToBinanceCandle(List<Object> candleData) {
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

        return new BinanceCandle(
                klineOpenTime, openPrice, highPrice, lowPrice, closePrice,
                volume, klineCloseTime, quoteAssetVolume, numberOfTrades,
                takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume
        );
    }


}
