package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.List;

@Getter
public class BithumbOrderBookDetail {

    private String orderCurrency;

    private List<BithumbOrder> bids;

    private List<BithumbOrder> asks;

}
