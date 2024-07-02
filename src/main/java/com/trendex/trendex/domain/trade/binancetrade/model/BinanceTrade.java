package com.trendex.trendex.domain.trade.binancetrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BinanceTrade {

    @Id
    private String id;

    private String symbol;

    private String price;

    private String qty;

    private String quoteQty;

    private long timestamp;

    private boolean isBuyerMaker;

    private boolean isBestMatch;

    public BinanceTrade(String id, String symbol, String price, String qty, String quoteQty, long timestamp, boolean isBuyerMaker, boolean isBestMatch) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.qty = qty;
        this.quoteQty = quoteQty;
        this.timestamp = timestamp;
        this.isBuyerMaker = isBuyerMaker;
        this.isBestMatch = isBestMatch;
    }
}
