package com.trendex.trendex.domain.macd.upbitmacd.dto;

public class UpbitMacdResponse {

    private String market;

    private String decision;

    private Double macdValue;

    private Double macdSignalValue;

    public UpbitMacdResponse(String market, String decision, Double macdValue, Double macdSignalValue) {
        this.market = market;
        this.decision = decision;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
    }
}
