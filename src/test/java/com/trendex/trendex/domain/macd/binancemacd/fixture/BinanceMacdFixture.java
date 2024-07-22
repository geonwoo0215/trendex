package com.trendex.trendex.domain.macd.binancemacd.fixture;

import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class BinanceMacdFixture {

    private static final Double MACD_VALUE = 0.0;
    private static final Double MACD_SIGNAL_VALUE = 0.0;

    public static BinanceMacd createBinanceMacd(String symbol, Long timestamp) {

        return new BinanceMacd(symbol, MACD_VALUE, MACD_SIGNAL_VALUE, timestamp);
    }

    public static List<BinanceMacd> createBinanceMacds(List<String> markets, Long latestTimestamp) {
        return markets.stream()
                .flatMap(market -> LongStream.range(0, latestTimestamp + 1)
                        .mapToObj(i -> createBinanceMacd(market, i)))
                .collect(Collectors.toList());
    }


}
