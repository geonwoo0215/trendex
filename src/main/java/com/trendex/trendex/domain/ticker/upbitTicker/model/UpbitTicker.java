package com.trendex.trendex.domain.ticker.upbitTicker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpbitTicker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private String tradeDate;

    private String tradeTime;

    private String tradeDateKst;

    private String tradeTimeKst;

    private Long tradeTimestamp;

    private Long openingPrice;

    private Long highPrice;

    private Long lowPrice;

    private Long tradePrice;

    private Long prevClosingPrice;

    private String change;

    private Long changePrice;

    private Double changeRate;

    private Long signedChangePrice;

    private Double signedChangeRate;

    private Double tradeVolume;

    private Double accTradePrice;

    private Double accTradePrice24h;

    private Double accTradeVolume;

    private Double accTradeVolume24h;

    private Long highest52WeekPrice;

    private String highest52WeekDate;

    private Long lowest52WeekPrice;

    private String lowest52WeekDate;

    private Long timestamp;

    public UpbitTicker(String market, String tradeDate, String tradeTime, String tradeDateKst, String tradeTimeKst, Long tradeTimestamp, Long openingPrice, Long highPrice, Long lowPrice, Long tradePrice, Long prevClosingPrice, String change, Long changePrice, Double changeRate, Long signedChangePrice, Double signedChangeRate, Double tradeVolume, Double accTradePrice, Double accTradePrice24h, Double accTradeVolume, Double accTradeVolume24h, Long highest52WeekPrice, String highest52WeekDate, Long lowest52WeekPrice, String lowest52WeekDate, Long timestamp) {
        this.market = market;
        this.tradeDate = tradeDate;
        this.tradeTime = tradeTime;
        this.tradeDateKst = tradeDateKst;
        this.tradeTimeKst = tradeTimeKst;
        this.tradeTimestamp = tradeTimestamp;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradePrice = tradePrice;
        this.prevClosingPrice = prevClosingPrice;
        this.change = change;
        this.changePrice = changePrice;
        this.changeRate = changeRate;
        this.signedChangePrice = signedChangePrice;
        this.signedChangeRate = signedChangeRate;
        this.tradeVolume = tradeVolume;
        this.accTradePrice = accTradePrice;
        this.accTradePrice24h = accTradePrice24h;
        this.accTradeVolume = accTradeVolume;
        this.accTradeVolume24h = accTradeVolume24h;
        this.highest52WeekPrice = highest52WeekPrice;
        this.highest52WeekDate = highest52WeekDate;
        this.lowest52WeekPrice = lowest52WeekPrice;
        this.lowest52WeekDate = lowest52WeekDate;
        this.timestamp = timestamp;
    }
}
