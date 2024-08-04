package com.trendex.trendex.domain.orderbook.upbitorderbook.fiture;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBookUnit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitOrderBookUnitFixture {

    private static final Long ASK_PRICE = 50000L;
    private static final Long BID_PRICE = 49000L;
    private static final Double ASK_SIZE = 0.2;
    private static final Double BID_SIZE = 0.3;

    public static UpbitOrderBookUnit createUpbitOrderBookUnit() {
        return new UpbitOrderBookUnit(
                ASK_PRICE,
                BID_PRICE,
                ASK_SIZE,
                BID_SIZE
        );

    }

    public static List<UpbitOrderBookUnit> createUpbitOrderBookUnits() {
        return LongStream.range(0, 10)
                .mapToObj((i -> createUpbitOrderBookUnit()))
                .collect(Collectors.toList());
    }

}
