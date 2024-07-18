package com.trendex.trendex.domain.rsi.binancersi.fixture;

import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class BinanceRsiFixture {

    private static final Double VOLUME = 0.0;

    public static BinanceRsi createBinanceRsi(String symbol, Long timestamp) {

        return new BinanceRsi(symbol, VOLUME, timestamp);

    }

    public static List<BinanceRsi> createBinanceRsis(List<String> symbols, Long latestTimestamp) {
        return symbols.stream()
                .flatMap(symbol -> LongStream.range(0, latestTimestamp + 1)
                        .mapToObj(i -> createBinanceRsi(symbol, i)))
                .collect(Collectors.toList());
    }

}
