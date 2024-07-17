package com.trendex.trendex.domain.macd.binancemacd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BinanceMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double macdValue;

    private Double macdSignalValue;

    private boolean signalHigherThanMacd;

    private Long timestamp;

    public BinanceMacd(String symbol, Double macdValue, Double macdSignalValue, Long timestamp) {
        this.symbol = symbol;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
        this.signalHigherThanMacd = macdSignalValue > macdValue;
        this.timestamp = timestamp;
    }

}
