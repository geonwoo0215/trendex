package com.trendex.trendex.domain.rsi.upbitrsi.fixture;

import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitRsiFixture {

    private static final Double VOLUME = 0.0;

    public static UpbitRsi createUpbitRsi(String market, Long timestamp) {

        return new UpbitRsi(market, VOLUME, timestamp);

    }

    public static List<UpbitRsi> createUpbitRsis(List<String> markets, Long latestTimestamp) {
        return markets.stream()
                .flatMap(market -> LongStream.range(0, latestTimestamp + 1)
                        .mapToObj(i -> createUpbitRsi(market, i)))
                .collect(Collectors.toList());
    }
}
