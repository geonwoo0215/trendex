package com.trendex.trendex.domain.orderbooktradeaggregate.fixture;

import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;

import java.time.LocalDateTime;

public class OrderBookTradeAggregateFixture {


    private static final Double AVG_TRADE_PRICE = 1.0;
    private static final Double TOTAL_TRADE_VOLUME = 2.0;
    private static final Double AVG_ASK_PRICE = 3.0;
    private static final Double AVG_BID_PRICE = 4.0;
    private static final Double TOTAL_ASK_SIZE = 5.0;
    private static final Double TOTAL_BID_SIZE = 6.0;

    public static OrderbookTradeAggregate createOrderbookTradeAggregate(String market, LocalDateTime startTime) {

        return new OrderbookTradeAggregate(market, AVG_TRADE_PRICE, TOTAL_TRADE_VOLUME, AVG_ASK_PRICE, AVG_BID_PRICE, TOTAL_ASK_SIZE, TOTAL_BID_SIZE, startTime);

    }


}
