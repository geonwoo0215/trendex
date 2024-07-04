package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;

public class UpbitOrderBookData {

    private long askPrice;

    private long bidPrice;

    private double askSize;

    private double bidSize;

    public UpbitOrderBook toUpbitOrderBook(String market) {
        return new UpbitOrderBook(market, askPrice, bidPrice, askSize, bidSize);
    }

}
