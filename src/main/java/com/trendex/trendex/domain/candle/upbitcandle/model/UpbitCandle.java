package com.trendex.trendex.domain.candle.upbitcandle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpbitCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private LocalDateTime candleDateTimeUtc;

    private LocalDateTime candleDateTimeKst;

    private double openingPrice;

    private double highPrice;

    private double lowPrice;

    private double tradePrice;

    private long timestamp;

    private double candleAccTradePrice;

    private double volume;

    private int unit;

    public UpbitCandle(String market, LocalDateTime candleDateTimeUtc, LocalDateTime candleDateTimeKst, double openingPrice, double highPrice, double lowPrice, double tradePrice, long timestamp, double candleAccTradePrice, double candleAccTradeVolume, int unit) {
        this.market = market;
        this.candleDateTimeUtc = candleDateTimeUtc;
        this.candleDateTimeKst = candleDateTimeKst;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradePrice = tradePrice;
        this.timestamp = timestamp;
        this.candleAccTradePrice = candleAccTradePrice;
        this.volume = candleAccTradeVolume;
        this.unit = unit;
    }
}
