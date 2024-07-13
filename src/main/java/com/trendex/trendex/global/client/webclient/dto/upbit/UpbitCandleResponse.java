package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpbitCandleResponse {

    @JsonProperty("market")
    private String market;

    @JsonProperty("candle_date_time_utc")
    private LocalDateTime candleDateTimeUtc;

    @JsonProperty("candle_date_time_kst")
    private LocalDateTime candleDateTimeKst;

    @JsonProperty("opening_price")
    private double openingPrice;

    @JsonProperty("high_price")
    private double highPrice;

    @JsonProperty("low_price")
    private double lowPrice;

    @JsonProperty("trade_price")
    private double tradePrice;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("candle_acc_trade_price")
    private double candleAccTradePrice;

    @JsonProperty("candle_acc_trade_volume")
    private String candleAccTradeVolume;

    private int unit;

    public UpbitCandle toUpbitCandle() {
        return new UpbitCandle(market, candleDateTimeUtc, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, timestamp, candleAccTradePrice, candleAccTradeVolume, unit);
    }

}
