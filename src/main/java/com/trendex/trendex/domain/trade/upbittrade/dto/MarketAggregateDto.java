package com.trendex.trendex.domain.trade.upbittrade.dto;

public interface MarketAggregateDto {

    String getMarket();

    Double getAvgTradePrice();

    Double getTotalTradeVolume();

    Double getAvgAskPrice();

    Double getAvgBidPrice();

    Double getTotalAskSize();

    Double getTotalBidSize();
}
