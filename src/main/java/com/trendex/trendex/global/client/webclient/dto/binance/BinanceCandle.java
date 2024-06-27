package com.trendex.trendex.global.client.webclient.dto.binance;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class BinanceCandle {

    private long klineOpenTime;

    private String openPrice;

    private String highPrice;

    private String lowPrice;

    private String closePrice;

    private String volume;

    private long klineCloseTime;

    private String quoteAssetVolume;

    private int numberOfTrades;

    private String takerBuyBaseAssetVolume;

    private String takerBuyQuoteAssetVolume;

    public BinanceCandle(long klineOpenTime, String openPrice, String highPrice, String lowPrice, String closePrice, String volume, long klineCloseTime, String quoteAssetVolume, int numberOfTrades, String takerBuyBaseAssetVolume, String takerBuyQuoteAssetVolume) {
        this.klineOpenTime = klineOpenTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.klineCloseTime = klineCloseTime;
        this.quoteAssetVolume = quoteAssetVolume;
        this.numberOfTrades = numberOfTrades;
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
    }

    public CryptoCandle toCryptoCandle(String symbol) {
        return new CryptoCandle("Binance", symbol, Double.parseDouble(volume), LocalDateTime.ofInstant(Instant.ofEpochMilli(klineCloseTime), ZoneId.systemDefault()), Double.parseDouble(openPrice), Double.parseDouble(highPrice), Double.parseDouble(lowPrice), Double.parseDouble(closePrice), Double.parseDouble(quoteAssetVolume));
    }
}
