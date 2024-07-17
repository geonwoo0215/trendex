package com.trendex.trendex.domain.rsi.upbitrsi.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_upbit_rsi_market_timestamp", columnList = "market, timestamp")
)
public class UpbitRsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double value;

    private Long timestamp;

    public UpbitRsi(String market, Double value, Long timestamp) {
        this.market = market;
        this.value = value;
        this.timestamp = timestamp;
    }

}
