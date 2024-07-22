package com.trendex.trendex.domain.upbitcandle.fixture;

import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitCandleFixture {

    private static final String MARKET = "BTC-USDT";
    private static final LocalDateTime DATETIME = LocalDateTime.now();
    private static final double PRICE = 0.0;
    private static final long TIMESTAMP = 0L;
    private static final double ACC_TRADE_PRICE = 0.0;
    private static final String VOLUME = "0";
    private static final int UNIT = 1;


    public static UpbitCandle createUpbitCandle(String market, long timestamp, String volume) {

        return new UpbitCandle(
                market,
                DATETIME,
                DATETIME,
                PRICE,
                PRICE,
                PRICE,
                PRICE,
                timestamp,
                ACC_TRADE_PRICE,
                volume,
                UNIT
        );

    }

    public static UpbitCandle createUpbitCandle(String market, long timestamp, Double tradePrice) {

        return new UpbitCandle(
                market,
                DATETIME,
                DATETIME,
                PRICE,
                PRICE,
                PRICE,
                tradePrice,
                timestamp,
                ACC_TRADE_PRICE,
                VOLUME,
                UNIT
        );

    }

    public static List<UpbitCandle> createUpbitCandles(List<String> markets, Long latestTimestamp, String volume) {
        return markets.stream()
                .flatMap(market -> LongStream.range(0, latestTimestamp + 1)
                        .mapToObj(i -> createUpbitCandle(market, i, volume)))
                .collect(Collectors.toList());
    }

}
