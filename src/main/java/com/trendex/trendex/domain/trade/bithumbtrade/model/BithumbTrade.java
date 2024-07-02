package com.trendex.trendex.domain.trade.bithumbtrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BithumbTrade {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;

    private String transactionDate;

    private String type;

    private String unitsTraded;

    private String price;

    private String total;

    public BithumbTrade(String symbol, String transactionDate, String type, String unitsTraded, String price, String total) {
        this.symbol = symbol;
        this.transactionDate = transactionDate;
        this.type = type;
        this.unitsTraded = unitsTraded;
        this.price = price;
        this.total = total;
    }
}
