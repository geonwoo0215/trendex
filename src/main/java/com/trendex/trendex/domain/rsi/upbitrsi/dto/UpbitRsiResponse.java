package com.trendex.trendex.domain.rsi.upbitrsi.dto;

public class UpbitRsiResponse {

    private String market;

    private String decision;

    private Double value;

    public UpbitRsiResponse(String market, String decision, Double value) {
        this.market = market;
        this.decision = decision;
        this.value = value;
    }
}
