package com.trendex.trendex.domain.macd.upbitmacd.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double value;

    private Long timestamp;

    @OneToOne(mappedBy = "upbitMacd")
    private UpbitMacdSignal upbitMacdSignal;

    public UpbitMacd(String symbol, Double value, Long timestamp) {
        this.symbol = symbol;
        this.value = value;
        this.timestamp = timestamp;
    }

}
