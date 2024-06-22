package com.trendex.trendex.global.client.webclient.dto.upbit;

import lombok.Getter;

@Getter
public class UpbitTrade {

    private String market;

    private String tradeDateUtc;

    private String tradeTimeUtc;

    private long timestamp;

    private long tradePrice;

    private double tradeVolume;

    private long prevClosingPrice;

    private long changePrice;

    private String askBid;

}
