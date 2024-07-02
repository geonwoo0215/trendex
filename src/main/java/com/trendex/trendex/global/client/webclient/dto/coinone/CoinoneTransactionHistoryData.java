package com.trendex.trendex.global.client.webclient.dto.coinone;

import com.trendex.trendex.domain.trade.coinonetrade.model.CoinoneTrade;
import lombok.Getter;

@Getter
public class CoinoneTransactionHistoryData {

    private String id;

    private long timestamp;

    private String price;

    private String qty;

    private boolean isSellerMaker;

    public CoinoneTrade toCoinoneTrade(String symbol) {
        return new CoinoneTrade(id, symbol, timestamp, price, qty, isSellerMaker);
    }

}
