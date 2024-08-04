package com.trendex.trendex.domain.trade.upbittrade.fiture;

import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UpbitTradeFixture {

    private static final Long ID = 1L;
    private static final String MARKET = "KRW-BTC";
    private static final String TRADE_DATE_UTC = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    private static final String TRADE_TIME_UTC = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    private static final long TIMESTAMP = 3L;
    private static final long TRADE_PRICE = 50000L;
    private static final double TRADE_VOLUME = 0.1;
    private static final long PREV_CLOSING_PRICE = 49000L;
    private static final long CHANGE_PRICE = 1000L;
    private static final String ASK_BID = "ASK";
    private static final Long SEQUENTIAL_ID = 1000L;

    public static UpbitTrade createUpbitTrade() {
        return new UpbitTrade(
                MARKET,
                TRADE_DATE_UTC,
                TRADE_TIME_UTC,
                TIMESTAMP,
                TRADE_PRICE,
                TRADE_VOLUME,
                PREV_CLOSING_PRICE,
                CHANGE_PRICE,
                ASK_BID,
                SEQUENTIAL_ID
        );
    }

    public static UpbitTrade createUpbitTrade(Long timestamp, String market) {
        return new UpbitTrade(
                market,
                TRADE_DATE_UTC,
                TRADE_TIME_UTC,
                timestamp,
                TRADE_PRICE,
                TRADE_VOLUME,
                PREV_CLOSING_PRICE,
                CHANGE_PRICE,
                ASK_BID,
                SEQUENTIAL_ID
        );
    }

    public static List<UpbitTrade> createUpbitTrades(Long count, String market) {

        return LongStream.range(0, count)
                .mapToObj(i -> createUpbitTrade(Timestamp.valueOf(LocalDateTime.now().minusHours(3L).plusHours(i)).getTime(), market))
                .collect(Collectors.toList());
    }
}
