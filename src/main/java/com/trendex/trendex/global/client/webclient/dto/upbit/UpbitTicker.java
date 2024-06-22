package com.trendex.trendex.global.client.webclient.dto.upbit;

import lombok.Getter;

@Getter
public class UpbitTicker {

    private String market;

    private String tradeDate;

    private String tradeTime;

    private String tradeDateKst;

    private String tradeTimeKst;

    private long tradeTimestamp;

    private long openingPrice;

    private long highPrice;

    private long lowPrice;

    private long tradePrice;

    private long prevClosingPrice;

    private String change;

    private long changePrice;

    private double changeRate;

    private long signedChangePrice;

    private double signedChangeRate;

    private double tradeVolume;

    private double accTradePrice;

    private double accTradePrice24h;

    private double accTradeVolume;

    private double accTradeVolume24h;

    private long highest52WeekPrice;

    private String highest52WeekDate;

    private long lowest52WeekPrice;

    private String lowest52WeekDate;

    private long timestamp;

}
