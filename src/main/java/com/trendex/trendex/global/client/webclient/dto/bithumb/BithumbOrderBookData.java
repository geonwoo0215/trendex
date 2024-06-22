package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.Map;

@Getter
public class BithumbOrderBookData {

    private String timestamp;

    private String paymentCurrency;

    private Map<String, BithumbOrderBookDetail> currencies;

}
