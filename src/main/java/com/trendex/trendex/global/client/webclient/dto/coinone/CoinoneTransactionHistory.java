package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

import java.util.List;

@Getter
public class CoinoneTransactionHistory {

    private String result;

    private String errorCode;

    private long serverTime;

    private String quoteCurrency;

    private String targetCurrency;

    private List<CoinoneTransactionHistoryData> transactions;

}
