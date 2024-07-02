package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpbitCandleResponse {

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

    public UpbitCandle toUpbitCandle() {
        return new UpbitCandle(market, candleDateTimeUtc, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, timestamp, candleAccTradePrice, candleAccTradeVolume, unit);
    }

}
