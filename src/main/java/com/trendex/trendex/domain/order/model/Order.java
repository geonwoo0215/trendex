package com.trendex.trendex.domain.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private String uuid;

    private String side;

    private String ordType;

    private double price;

    private String state;

    private String market;

    private LocalDateTime createdAt;

    private double volume;

    private double remainingVolume;

    private double reservedFee;

    private double remainingFee;

    private double paidFee;

    private double locked;

    private double executedVolume;

    private int tradesCount;

    private int timeInForce;

    public Order(String uuid, String side, String ordType, double price, String state, String market, LocalDateTime createdAt, double volume, double remainingVolume, double reservedFee, double remainingFee, double paidFee, double locked, double executedVolume, int tradesCount, int timeInForce) {
        this.uuid = uuid;
        this.side = side;
        this.ordType = ordType;
        this.price = price;
        this.state = state;
        this.market = market;
        this.createdAt = createdAt;
        this.volume = volume;
        this.remainingVolume = remainingVolume;
        this.reservedFee = reservedFee;
        this.remainingFee = remainingFee;
        this.paidFee = paidFee;
        this.locked = locked;
        this.executedVolume = executedVolume;
        this.tradesCount = tradesCount;
        this.timeInForce = timeInForce;
    }
}
