package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

@Getter
public class CoinoneTransactionHistoryData {

    private String id;

    private long timestamp;

    private String price;

    private String qty;

    private boolean isSellerMaker;

}
