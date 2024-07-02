package com.trendex.trendex.domain.candle.bithumbcandle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BithumbCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private long timestamp;

    private String openPrice;

    private String highPrice;

    private String lowPrice;

    private String closePrice;

    private String volume;

    public BithumbCandle(String symbol, long timestamp, String openPrice, String highPrice, String lowPrice, String closePrice, String volume) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }
}
