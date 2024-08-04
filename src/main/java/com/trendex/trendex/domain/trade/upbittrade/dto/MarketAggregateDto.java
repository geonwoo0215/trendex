package com.trendex.trendex.domain.trade.upbittrade.dto;

public interface MarketAggregateDto {

    Double getAvgTradePrice();

    Double getTotalTradeVolume();

    Double getAvgAskPrice();

    Double getAvgBidPrice();

    Double getTotalAskSize();

    Double getTotalBidSize();
}
