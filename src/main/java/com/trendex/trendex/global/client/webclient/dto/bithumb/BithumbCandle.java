package com.trendex.trendex.global.client.webclient.dto.bithumb;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class BithumbCandle {

    private String status;

    private List<List<Object>> data;

    public List<CryptoCandle> toCryptoCandleList(String symbol) {
        return Optional.ofNullable(data)
                .orElse(Collections.emptyList())
                .stream()
                .map(candle -> convertToCryptoCandle(symbol, candle))
                .collect(Collectors.toList());
    }

    private CryptoCandle convertToCryptoCandle(String symbol, List<Object> candle) {
        return new CryptoCandle(
                "Bithumb",
                symbol,
                Double.parseDouble((String) candle.get(5)),
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) candle.get(0)), ZoneId.systemDefault()),
                Double.parseDouble((String) candle.get(1)),
                Double.parseDouble((String) candle.get(2)),
                Double.parseDouble((String) candle.get(3)),
                Double.parseDouble((String) candle.get(4)),
                null
        );
    }

}
