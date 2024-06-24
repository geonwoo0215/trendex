package com.trendex.trendex.global.client.webclient.dto.binance;

import lombok.Getter;

@Getter
public class BinanceTrade {

    private String id;

    private String price;

    private String qty;

    private String quoteQty;

    private long timestamp;

    private boolean isBuyerMaker;

    private boolean isBestMatch;

}
