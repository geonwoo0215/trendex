package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBookUnit;

public class UpbitOrderBookUnitResponse {

    @JsonProperty("aid_price")
    private Long askPrice;

    @JsonProperty("bid_price")
    private Long bidPrice;

    @JsonProperty("ask_size")
    private Double askSize;

    @JsonProperty("bid_size")
    private Double bidSize;

    public UpbitOrderBookUnit toUpbitOrderBookUnits() {
        return new UpbitOrderBookUnit(askPrice, bidPrice, askSize, bidSize);
    }

}
