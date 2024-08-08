package com.trendex.trendex.domain.orderbooktradeaggregate.model;

import com.trendex.trendex.domain.orderbooktradeaggregate.dto.response.OrderbookTradeAggregateResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderbookTradeAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double avgTradePrice;

    private Double totalTradeVolume;

    private Double avgAskPrice;

    private Double avgBidPrice;

    private Double totalAskSize;

    private Double totalBidSize;

    private LocalDateTime startTime;

    public OrderbookTradeAggregate(String market, Double avgTradePrice, Double totalTradeVolume, Double avgAskPrice, Double avgBidPrice, Double totalAskSize, Double totalBidSize, LocalDateTime startTime) {
        this.market = market;
        this.avgTradePrice = avgTradePrice;
        this.totalTradeVolume = totalTradeVolume;
        this.avgAskPrice = avgAskPrice;
        this.avgBidPrice = avgBidPrice;
        this.totalAskSize = totalAskSize;
        this.totalBidSize = totalBidSize;
        this.startTime = startTime;
    }

    public OrderbookTradeAggregateResponse toOrderbookTradeAggregateResponse() {
        return new OrderbookTradeAggregateResponse(avgTradePrice, totalTradeVolume, avgAskPrice, avgBidPrice, totalAskSize, totalBidSize);
    }
}
