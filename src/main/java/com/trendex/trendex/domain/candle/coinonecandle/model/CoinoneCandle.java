package com.trendex.trendex.domain.candle.coinonecandle.model;

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
public class CoinoneCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private long timestamp;

    private String open;

    private String high;

    private String low;

    private String close;

    private String targetVolume;

    private String quoteVolume;

    public CoinoneCandle(String symbol, long timestamp, String open, String high, String low, String close, String targetVolume, String quoteVolume) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.targetVolume = targetVolume;
        this.quoteVolume = quoteVolume;
    }
}
