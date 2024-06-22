package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

import java.util.List;

@Getter
public class CoinoneTickerDetail {

    private String quoteCurrency;

    private String targetCurrency;

    private long timestamp;

    private String high;

    private String low;

    private String first;

    private String last;

    private String quoteVolume;

    private String targetVolume;

    private List<CoinoneOrder> bestAsks;

    private List<CoinoneOrder> bestBids;

    private String id;

}
