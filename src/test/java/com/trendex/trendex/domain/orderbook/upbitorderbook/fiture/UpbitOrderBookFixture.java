package com.trendex.trendex.domain.orderbook.upbitorderbook.fiture;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitOrderBookFixture {

    private static final String MARKET = "KRW-BTC";
    private static final Long TIMESTAMP = System.currentTimeMillis();
    private static final Double TOTAL_ASK_SIZE = 1.0;
    private static final Double TOTAL_BID_SIZE = 1.5;

    public static UpbitOrderBook createUpbitOrderBook() {
        return new UpbitOrderBook(
                MARKET,
                TIMESTAMP,
                TOTAL_ASK_SIZE,
                TOTAL_BID_SIZE
        );
    }

    public static UpbitOrderBook createUpbitOrderBook(Long timestamp, String market) {
        return new UpbitOrderBook(
                market,
                timestamp,
                TOTAL_ASK_SIZE,
                TOTAL_BID_SIZE
        );
    }

    public static List<UpbitOrderBook> createUpbitOrderBookUnits(Long count, String market) {
        return LongStream.range(0, count)
                .mapToObj((i -> createUpbitOrderBook(Timestamp.valueOf(LocalDateTime.now().minusHours(3L).plusHours(i)).getTime(), market)))
                .collect(Collectors.toList());
    }

}
