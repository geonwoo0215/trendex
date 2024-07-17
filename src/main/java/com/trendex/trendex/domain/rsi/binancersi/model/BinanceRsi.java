package com.trendex.trendex.domain.rsi.binancersi.model;

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
public class BinanceRsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double value;

    private Long timestamp;

    public BinanceRsi(String symbol, Double value, Long timestamp) {
        this.symbol = symbol;
        this.value = value;
        this.timestamp = timestamp;
    }
}
