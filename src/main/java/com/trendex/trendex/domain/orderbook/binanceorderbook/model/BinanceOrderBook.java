package com.trendex.trendex.domain.orderbook.binanceorderbook.model;

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
public class BinanceOrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private String askBid;

    private Double price;

    private Double qty;

    public BinanceOrderBook(String symbol, String askBid, Double price, Double qty) {
        this.symbol = symbol;
        this.askBid = askBid;
        this.price = price;
        this.qty = qty;
    }
}
