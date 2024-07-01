package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public CryptoCandle toCryptoCandle(String symbol) {
        return new CryptoCandle("Upbit", symbol, candleAccTradeVolume, LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()), openingPrice, highPrice, lowPrice, tradePrice, candleAccTradeVolume);
    }

}
