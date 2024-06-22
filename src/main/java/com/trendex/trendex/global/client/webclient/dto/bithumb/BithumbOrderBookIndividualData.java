package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.List;

@Getter
public class BithumbOrderBookIndividualData {

    private String timestamp;

    private String paymentCurrency;

    private String orderCurrency;

    private List<BithumbOrder> bids;

    private List<BithumbOrder> asks;

}
