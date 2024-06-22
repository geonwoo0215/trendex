package com.trendex.trendex.global.client.webclient.dto.coinone;

import jakarta.persistence.criteria.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class CoinoneOrderBook {

    private String result;

    private String errorCode;

    private long timestamp;

    private String id;

    private String quoteCurrency;

    private String targetCurrency;

    private String orderBookUnit;

    private List<CoinoneOrder> bids;

    private List<CoinoneOrder> asks;

}
