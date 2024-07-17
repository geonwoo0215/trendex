package com.trendex.trendex.domain.binancecandle.fixture;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

public class BinanceCandleFixture {

    private static final String symbol = "BTCUSDT";
    private static final long klineOpenTime = LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000;
    private static final String openPrice = "40000.00";
    private static final String highPrice = "40500.00";
    private static final String lowPrice = "39500.00";
    private static final String closePrice = "40200.00";
    private static final String volume = "1500.123";
    private static final long klineCloseTime = LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000;
    private static final String quoteAssetVolume = "60000000.00";
    private static final int numberOfTrades = 4500;
    private static final String takerBuyBaseAssetVolume = "800.567";
    private static final String takerBuyQuoteAssetVolume = "32000000.00";


    public static BinanceCandle createBinanceCandle() {

        return new BinanceCandle(symbol, klineOpenTime, openPrice, highPrice, lowPrice, closePrice, volume, klineCloseTime, quoteAssetVolume, numberOfTrades, takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume);

    }

    public static BinanceCandle createBinanceCandle(long minusMinutes) {

        long klineCloseTime = LocalDateTime.now().minusMinutes(minusMinutes).toEpochSecond(ZoneOffset.UTC) * 1000;

        return new BinanceCandle(symbol, klineOpenTime, openPrice, highPrice, lowPrice, closePrice, volume, klineCloseTime, quoteAssetVolume, numberOfTrades, takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume);

    }

    public static List<BinanceCandle> createBinanceCandles(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createBinanceCandle())
                .toList();
    }

}
