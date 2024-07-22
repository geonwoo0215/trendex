package com.trendex.trendex.global.client.webclient.dto.binance;

import lombok.Getter;

@Getter
public class BinanceOrderBookTicker {

    private String symbol;

    private String bidPrice;

    private String bidQty;

    private String askPrice;

    private String askQty;

}
