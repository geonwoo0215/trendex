package com.trendex.trendex.domain.binancesymbol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BinanceSymbol {

    @Id
    private String symbol;

    public BinanceSymbol(String symbol) {
        this.symbol = symbol;
    }
}
