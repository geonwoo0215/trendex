package com.trendex.trendex.domain.trade.upbittrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitTrade {

    @Id
    private Long id;

    private String market;

    private String tradeDateUtc;

    private String tradeTimeUtc;

    private long timestamp;

    private long tradePrice;

    private double tradeVolume;

    private long prevClosingPrice;

    private long changePrice;

    private String askBid;

    public UpbitTrade(Long id, String market, String tradeDateUtc, String tradeTimeUtc, long timestamp, long tradePrice, double tradeVolume, long prevClosingPrice, long changePrice, String askBid) {
        this.id = id;
        this.market = market;
        this.tradeDateUtc = tradeDateUtc;
        this.tradeTimeUtc = tradeTimeUtc;
        this.timestamp = timestamp;
        this.tradePrice = tradePrice;
        this.tradeVolume = tradeVolume;
        this.prevClosingPrice = prevClosingPrice;
        this.changePrice = changePrice;
        this.askBid = askBid;
    }
}
