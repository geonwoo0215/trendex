package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.order.model.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpbitOrderResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("side")
    private String side;

    @JsonProperty("ord_type")
    private String ordType;

    @JsonProperty("price")
    private double price;

    @JsonProperty("state")
    private String state;

    @JsonProperty("market")
    private String market;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("remaining_volume")
    private double remainingVolume;

    @JsonProperty("reserved_fee")
    private double reservedFee;

    @JsonProperty("remaining_fee")
    private double remainingFee;

    @JsonProperty("paid_fee")
    private double paidFee;

    @JsonProperty("locked")
    private double locked;

    @JsonProperty("executed_volume")
    private double executedVolume;

    @JsonProperty("trades_count")
    private int tradesCount;

    @JsonProperty("time_in_force")
    private int timeInForce;

    public Order toOrder() {
        return new Order(uuid, side, ordType, price, state, market, createdAt, volume, remainingVolume, reservedFee, remainingFee, paidFee, locked, executedVolume, tradesCount, timeInForce);
    }

}
