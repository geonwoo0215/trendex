package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

@Getter
public class CoinoneCurrencyDetail {

    private String name;

    private String symbol;

    private String depositStatus;

    private String withdrawStatus;

    private int depositConfirmCount;

    private int maxPrecision;

    private String depositFee;

    private String withdrawalMinAmount;

    private String withdrawalFee;

}
