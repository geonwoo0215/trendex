package com.trendex.trendex.global.client.webclient.dto.binance;

import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import lombok.Getter;

@Getter
public class BinanceTradeResponse {

    private String id;

    private String price;

    private String qty;

    private String quoteQty;

    private long timestamp;

    private boolean isBuyerMaker;

    private boolean isBestMatch;

    public BinanceTrade toBinanceTrade(String symbol) {
        return new BinanceTrade(id, symbol, price, qty, quoteQty, timestamp, isBuyerMaker, isBestMatch);
    }

}
