package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBookUnit;
import lombok.Getter;

import java.util.List;

@Getter
public class UpbitOrderBookResponse {

    @JsonProperty("market")
    private String market;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("total_ask_size")
    private double totalAskSize;

    @JsonProperty("total_bid_size")
    private double totalBidSize;

    private List<UpbitOrderBookUnitResponse> upbitOrderBookUnitResponses;

    @JsonProperty("level")
    private int level;

    public UpbitOrderBook toUpbitOrderBook() {

        List<UpbitOrderBookUnit> upbitOrderBookUnits = upbitOrderBookUnitResponses.stream()
                .map(UpbitOrderBookUnitResponse::toUpbitOrderBookUnits)
                .toList();

        UpbitOrderBook upbitOrderBook = new UpbitOrderBook(market, timestamp, totalAskSize, totalBidSize);

        upbitOrderBookUnits.forEach(upbitOrderBook::addUpbitOrderBookUnits);

        return upbitOrderBook;

    }


}
