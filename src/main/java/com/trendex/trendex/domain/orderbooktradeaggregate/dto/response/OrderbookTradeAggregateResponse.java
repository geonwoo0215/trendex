package com.trendex.trendex.domain.orderbooktradeaggregate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "OrderbookTradeAggregate DTO")
@Getter
public class OrderbookTradeAggregateResponse {

    @Schema(description = "평균 거래 가격")
    private Double avgTradePrice;

    @Schema(description = "총 거래량")
    private Double totalTradeVolume;

    @Schema(description = "평균 매도 가격")
    private Double avgAskPrice;

    @Schema(description = "평균 매수 가격")
    private Double avgBidPrice;

    @Schema(description = "총 매도 수량")
    private Double totalAskSize;

    @Schema(description = "총 매수 수량")
    private Double totalBidSize;

    public OrderbookTradeAggregateResponse(Double avgTradePrice, Double totalTradeVolume, Double avgAskPrice, Double avgBidPrice, Double totalAskSize, Double totalBidSize) {
        this.avgTradePrice = avgTradePrice;
        this.totalTradeVolume = totalTradeVolume;
        this.avgAskPrice = avgAskPrice;
        this.avgBidPrice = avgBidPrice;
        this.totalAskSize = totalAskSize;
        this.totalBidSize = totalBidSize;
    }
}
