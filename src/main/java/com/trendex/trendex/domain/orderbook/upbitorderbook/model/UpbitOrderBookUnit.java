package com.trendex.trendex.domain.orderbook.upbitorderbook.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpbitOrderBookUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long askPrice;

    private Long bidPrice;

    private Double askSize;

    private Double bidSize;

    @ManyToOne
    private UpbitOrderBook upbitOrderBook;


    public UpbitOrderBookUnit(Long askPrice, Long bidPrice, Double askSize, Double bidSize) {
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.askSize = askSize;
        this.bidSize = bidSize;
    }

    public void updateUpbitOrderBook(UpbitOrderBook upbitOrderBook) {
        this.upbitOrderBook = upbitOrderBook;
    }

}
