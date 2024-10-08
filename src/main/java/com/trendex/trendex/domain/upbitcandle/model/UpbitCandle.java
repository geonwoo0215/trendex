package com.trendex.trendex.domain.upbitcandle.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = @Index(name = "idx_upbit_candle_market_timestamp", columnList = "market, timestamp")
)
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

    private double closePrice;

    private long timestamp;

    private double candleAccTradePrice;

    private String volume;

    private int unit;

    public UpbitCandle(String market, LocalDateTime candleDateTimeUtc, LocalDateTime candleDateTimeKst, double openingPrice, double highPrice, double lowPrice, double tradePrice, long timestamp, double candleAccTradePrice, String candleAccTradeVolume, int unit) {
        this.market = market;
        this.candleDateTimeUtc = candleDateTimeUtc;
        this.candleDateTimeKst = candleDateTimeKst;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = tradePrice;
        this.timestamp = timestamp;
        this.candleAccTradePrice = candleAccTradePrice;
        this.volume = candleAccTradeVolume;
        this.unit = unit;
    }
}
