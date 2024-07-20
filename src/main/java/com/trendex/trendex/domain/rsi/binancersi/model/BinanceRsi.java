package com.trendex.trendex.domain.rsi.binancersi.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_binance_rsi_symbol_timestamp", columnList = "symbol, timestamp")
)
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
