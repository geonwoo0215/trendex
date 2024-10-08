package com.trendex.trendex.domain.binancecandle.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = @Index(name = "idx_binance_candle_market_klineCloseTime", columnList = "symbol, klineCloseTime")
)
public class BinanceCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private long klineOpenTime;

    private String openPrice;

    private String highPrice;

    private String lowPrice;

    private Double closePrice;

    private String volume;

    private long klineCloseTime;

    private String quoteAssetVolume;

    private int numberOfTrades;

    private String takerBuyBaseAssetVolume;

    private String takerBuyQuoteAssetVolume;

    public BinanceCandle(String symbol, long klineOpenTime, String openPrice, String highPrice, String lowPrice, Double closePrice, String volume, long klineCloseTime, String quoteAssetVolume, int numberOfTrades, String takerBuyBaseAssetVolume, String takerBuyQuoteAssetVolume) {
        this.symbol = symbol;
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
}
