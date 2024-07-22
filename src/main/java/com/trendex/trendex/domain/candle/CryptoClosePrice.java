package com.trendex.trendex.domain.candle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoClosePrice {

    private Double closePrice;

    private Long timeStamp;

    public CryptoClosePrice(Double closePrice, Long timeStamp) {
        this.closePrice = closePrice;
        this.timeStamp = timeStamp;
    }


}
