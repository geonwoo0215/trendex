package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

import java.util.List;

@Getter
public class CoinoneCurrency {

    private String result;

    private String errorCode;

    private long serverTime;

    private List<CoinoneCurrencyDetail> currencies;

}
