package com.trendex.trendex.domain.rsi.upbitrsi.model;

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
public class UpbitRsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double value;

    private Long timestamp;

    public UpbitRsi(String market, Double value) {
        this.market = market;
        this.value = value;
        this.timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;
    }
}
