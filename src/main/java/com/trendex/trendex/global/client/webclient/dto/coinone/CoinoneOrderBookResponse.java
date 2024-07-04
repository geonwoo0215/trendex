package com.trendex.trendex.global.client.webclient.dto.coinone;

import com.trendex.trendex.domain.orderbook.coinoneorderbook.model.CoinoneOrderBook;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class CoinoneOrderBookResponse {

    private String result;

    private String errorCode;

    private long timestamp;

    private String id;

    private String quoteCurrency;

    private String targetCurrency;

    private String orderBookUnit;

    private List<CoinoneOrder> bids;

    private List<CoinoneOrder> asks;

    public List<CoinoneOrderBook> toCoinoneOrderBook() {
        return Stream.concat(
                bids.stream().map(bid -> new CoinoneOrderBook(id, "bid", targetCurrency, orderBookUnit, Double.parseDouble(bid.getPrice()), Double.parseDouble(bid.getQty()))),
                asks.stream().map(ask -> new CoinoneOrderBook(id, "ask", targetCurrency, orderBookUnit, Double.parseDouble(ask.getPrice()), Double.parseDouble(ask.getQty())))
        ).collect(Collectors.toList());
    }

}
