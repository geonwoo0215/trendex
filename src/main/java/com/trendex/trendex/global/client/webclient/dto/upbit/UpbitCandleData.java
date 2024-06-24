package com.trendex.trendex.global.client.webclient.dto.upbit;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpbitCandleData {

    private String market;

    private LocalDateTime candleDateTimeUtc;

    private LocalDateTime candleDateTimeKst;

    private double openingPrice;

    private double highPrice;

    private double lowPrice;

    private double tradePrice;

    private long timestamp;

    private double candleAccTradePrice;

    private double candleAccTradeVolume;

    private int unit;

}
