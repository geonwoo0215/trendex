package com.trendex.trendex.domain.orderbook.coinoneorderbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CoinoneOrderBook {

    @Id
    private String id;

    private String askBid;

    private String targetCurrency;

    private String orderBookUnit;

    private Double price;

    private Double qty;

    public CoinoneOrderBook(String id, String askBid, String targetCurrency, String orderBookUnit, Double price, Double qty) {
        this.id = id;
        this.askBid = askBid;
        this.targetCurrency = targetCurrency;
        this.orderBookUnit = orderBookUnit;
        this.price = price;
        this.qty = qty;
    }
}
