package com.trendex.trendex.global.client.webclient.dto.binance;

import lombok.Getter;

@Getter
public class BinanceTicker {

    private String symbol;

    private String priceChange;

    private String priceChangePercent;

    private String weightedAvgPrice;

    private String prevClosePrice;

    private String lastPrice;

    private String lastQty;

    private String bidPrice;

    private String askPrice;

    private String openPrice;

    private String highPrice;

    private String lowPrice;

    private String volume;

    private String quoteVolume;

    private long openTime;

    private long closeTime;

    private long firstId;

    private long lastId;

    private long count;

}
