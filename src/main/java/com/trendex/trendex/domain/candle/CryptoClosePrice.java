package com.trendex.trendex.domain.candle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoClosePrice {

    private Double tradePrice;

    public CryptoClosePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }
}
