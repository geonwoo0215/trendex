package com.trendex.trendex.global.client.webclient.dto.binance;

import lombok.Getter;

import java.util.List;

@Getter
public class BinanceOrderBook {

    private long lastUpdateId;

    private List<List<String>> bids;

    private List<List<String>> asks;

}
