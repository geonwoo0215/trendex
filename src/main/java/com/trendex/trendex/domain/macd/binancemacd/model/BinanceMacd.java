package com.trendex.trendex.domain.macd.binancemacd.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_binance_macd_symbol_timestamp", columnList = "symbol, timestamp")
)
public class BinanceMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double macdValue;

    private Double macdSignalValue;

    private Boolean signalHigherThanMacd;

    private Long timestamp;

    public BinanceMacd(String symbol, Double macdValue, Double macdSignalValue, Long timestamp) {
        this.symbol = symbol;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
        this.signalHigherThanMacd = Objects.isNull(macdSignalValue) ? null : macdSignalValue > macdValue;
        this.timestamp = timestamp;
    }

}
