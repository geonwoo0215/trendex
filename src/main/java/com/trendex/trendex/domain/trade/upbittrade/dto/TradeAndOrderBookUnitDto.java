package com.trendex.trendex.domain.trade.upbittrade.dto;

import lombok.Getter;

@Getter
public class TradeAndOrderBookUnitDto {

    private String market;

    private Long timestamp;

    private Double tradePrice;

    private Double tradeVolume;

    private Double askPrice;

    private Double bidPrice;

    private Double askSize;

    private Double bidSize;

    public TradeAndOrderBookUnitDto(String market, Long timestamp, Double tradePrice, Double tradeVolume, Double askPrice, Double bidPrice, Double askSize, Double bidSize) {
        this.market = market;
        this.timestamp = timestamp;
        this.tradePrice = tradePrice;
        this.tradeVolume = tradeVolume;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.askSize = askSize;
        this.bidSize = bidSize;
    }
}
