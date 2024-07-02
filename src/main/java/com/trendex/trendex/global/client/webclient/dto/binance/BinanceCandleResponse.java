package com.trendex.trendex.global.client.webclient.dto.binance;

import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import lombok.Getter;

@Getter
public class BinanceCandleResponse {

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

    public BinanceCandleResponse(long klineOpenTime, String openPrice, String highPrice, String lowPrice, String closePrice, String volume, long klineCloseTime, String quoteAssetVolume, int numberOfTrades, String takerBuyBaseAssetVolume, String takerBuyQuoteAssetVolume) {
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

    public BinanceCandle toBinanceCandle(String symbol) {
        return new BinanceCandle(symbol, klineOpenTime, openPrice, highPrice, lowPrice, closePrice, volume, klineCloseTime, quoteAssetVolume, numberOfTrades, takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume);
    }
}
