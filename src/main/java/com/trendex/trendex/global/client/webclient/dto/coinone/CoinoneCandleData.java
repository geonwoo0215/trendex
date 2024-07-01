package com.trendex.trendex.global.client.webclient.dto.coinone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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


    public CryptoCandle toCryptoCandle(String symbol) {
        return new CryptoCandle("Coinone", symbol, Double.parseDouble(targetVolume), LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()), Double.parseDouble(open), Double.parseDouble(high), Double.parseDouble(low), Double.parseDouble(close), Double.parseDouble(quoteVolume));
    }


}
