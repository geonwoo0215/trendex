package com.trendex.trendex.global.client.webclient.dto.bithumb;

import com.trendex.trendex.domain.orderbook.bithumborderbook.model.BithumbOrderBook;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class BithumbOrderBookIndividualData {

    private String timestamp;

    private String paymentCurrency;

    private String orderCurrency;

    private List<BithumbOrder> bids;

    private List<BithumbOrder> asks;

    public List<BithumbOrderBook> toBithumbOrderBook() {
        return Stream.concat(
                bids.stream().map(bids -> new BithumbOrderBook(orderCurrency, "bid", Double.parseDouble(bids.getPrice()), Double.parseDouble(bids.getQuantity()))),
                asks.stream().map(asks -> new BithumbOrderBook(orderCurrency, "ask", Double.parseDouble(asks.getPrice()), Double.parseDouble(asks.getQuantity())))
        ).collect(Collectors.toList());
    }

}
