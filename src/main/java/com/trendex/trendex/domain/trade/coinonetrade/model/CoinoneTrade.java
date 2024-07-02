package com.trendex.trendex.domain.trade.coinonetrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoinoneTrade {

    @Id
    private String id;

    private String symbol;

    private long timestamp;

    private String price;

    private String qty;

    private boolean isSellerMaker;

    public CoinoneTrade(String id, String symbol, long timestamp, String price, String qty, boolean isSellerMaker) {
        this.id = id;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.price = price;
        this.qty = qty;
        this.isSellerMaker = isSellerMaker;
    }
}
