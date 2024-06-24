package com.trendex.trendex.global.client.webclient.dto.coinone;

import lombok.Getter;

@Getter
public class CoinoneCandleData {

    private long timestamp;

    private String open;

    private String high;

    private String low;

    private String close;

    private String targetVolume;

    private String quoteVolume;
}
