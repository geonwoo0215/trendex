package com.trendex.trendex.domain.orderbook.upbitorderbook.model;

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
public class UpbitOrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private long askPrice;

    private long bidPrice;

    private double askSize;

    private double bidSize;

    public UpbitOrderBook(String market, long askPrice, long bidPrice, double askSize, double bidSize) {
        this.market = market;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.askSize = askSize;
        this.bidSize = bidSize;
    }
}
