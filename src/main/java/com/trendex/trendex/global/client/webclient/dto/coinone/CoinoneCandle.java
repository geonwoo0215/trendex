package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

import java.util.List;

@Getter
public class CoinoneCandle {

    private String result;

    private String errorCode;

    private boolean isLast;

    private List<CoinoneCandleData> chart;
}
