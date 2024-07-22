package com.trendex.trendex.global.client.webclient.dto.binance;

import com.trendex.trendex.domain.orderbook.binanceorderbook.model.BinanceOrderBook;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class BinanceOrderBookResponse {

    private long lastUpdateId;

    private List<List<String>> bids;

    private List<List<String>> asks;


    public List<BinanceOrderBook> toBinanceOrderBook(String symbol) {
        return Stream.concat(
                bids.stream().map(bid -> new BinanceOrderBook(symbol, "bid", Double.parseDouble(bid.get(0)), Double.parseDouble(bid.get(1)))),
                asks.stream().map(ask -> new BinanceOrderBook(symbol, "ask", Double.parseDouble(ask.get(0)), Double.parseDouble(ask.get(1))))
        ).collect(Collectors.toList());
    }

}
