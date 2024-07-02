package com.trendex.trendex.global.client.webclient.dto.coinone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import lombok.Getter;

@Getter
public class CoinoneCandleData {

    private long timestamp;

    private String open;

    private String high;

    private String low;

    private String close;

    @JsonProperty("target_volume")
    private String targetVolume;

    @JsonProperty("quote_volume")
    private String quoteVolume;

    public CoinoneCandle toCoinoneCandle(String symbol) {
        return new CoinoneCandle(symbol, timestamp, open, high, low, close, targetVolume, quoteVolume);
    }

}
