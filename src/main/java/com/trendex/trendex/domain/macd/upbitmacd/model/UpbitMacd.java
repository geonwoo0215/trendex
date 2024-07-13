package com.trendex.trendex.domain.macd.upbitmacd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double macdValue;

    private Double macdSignalValue;

    private boolean signalHigherThanMacd;

    private Long timestamp;

    public UpbitMacd(String market, Double macdValue, Double macdSignalValue) {
        this.market = market;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
        this.signalHigherThanMacd = macdSignalValue > macdValue;
        this.timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;
    }

}
