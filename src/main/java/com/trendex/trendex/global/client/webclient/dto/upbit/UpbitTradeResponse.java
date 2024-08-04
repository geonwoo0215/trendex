package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import lombok.Getter;

@Getter
public class UpbitTradeResponse {

    private String market;

    private String tradeDateUtc;

    private String tradeTimeUtc;

    private long timestamp;

    private long tradePrice;

    private double tradeVolume;

    private long prevClosingPrice;

    private long changePrice;

    private String askBid;

    private long sequentialId;

    public UpbitTrade toUpbitTrade() {
        return new UpbitTrade(market, tradeDateUtc, tradeTimeUtc, timestamp, tradePrice, tradeVolume, prevClosingPrice, changePrice, askBid, sequentialId);
    }

}
