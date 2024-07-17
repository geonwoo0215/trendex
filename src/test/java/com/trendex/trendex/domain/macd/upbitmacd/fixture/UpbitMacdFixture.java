package com.trendex.trendex.domain.macd.upbitmacd.fixture;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitMacdFixture {

    private static final Double MACD_VALUE = 0.0;
    private static final Double MACD_SIGNAL_VALUE = 0.0;
    private static final Boolean SIGNAL_HIGHER_THAN_MACD = true;

    private static final Long TIME_STAMP = 0L;


    public static UpbitMacd createUpbitMacd(String market, Long timestamp) {

        return new UpbitMacd(market, MACD_VALUE, MACD_SIGNAL_VALUE, timestamp);

    }

    public static List<UpbitMacd> createUpbitMacds(List<String> markets, Long latestTimestamp) {
        return markets.stream()
                .flatMap(market -> LongStream.range(0, latestTimestamp + 1)
                        .mapToObj(i -> createUpbitMacd(market, i)))
                .collect(Collectors.toList());
    }

}
