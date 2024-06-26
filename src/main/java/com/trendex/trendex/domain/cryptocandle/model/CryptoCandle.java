package com.trendex.trendex.domain.cryptocandle.model;

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
public class CryptoCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exchangeName;

    private String symbol;

    private double volume;

    private LocalDateTime timestamp;

    private double openPrice;

    private double highPrice;

    private double lowPrice;

    private double closePrice;

    private Double quoteVolume;

    public CryptoCandle(String exchangeName, String symbol, double volume, LocalDateTime timestamp, double openPrice, double highPrice, double lowPrice, double closePrice, Double quoteVolume) {
        this.exchangeName = exchangeName;
        this.symbol = symbol;
        this.volume = volume;
        this.timestamp = timestamp;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.quoteVolume = quoteVolume;
    }
}
