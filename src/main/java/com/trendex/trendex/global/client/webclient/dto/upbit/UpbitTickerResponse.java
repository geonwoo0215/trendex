package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.trendex.trendex.domain.ticker.upbitTicker.model.UpbitTicker;
import lombok.Getter;

@Getter
public class UpbitTickerResponse {

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

    public UpbitTicker toUpbitTicker() {
        return new UpbitTicker(market, tradeDate, tradeTime, tradeDateKst, tradeTimeKst, tradeTimestamp, openingPrice, highPrice, lowPrice, tradePrice, prevClosingPrice, change, changePrice, changeRate, signedChangePrice, signedChangeRate, tradeVolume, accTradePrice, accTradePrice24h, accTradeVolume, accTradeVolume24h, highest52WeekPrice, highest52WeekDate, lowest52WeekPrice, lowest52WeekDate, timestamp);
    }


}
