package com.trendex.trendex.global.client.webclient.dto.bithumb;

import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class BithumbCandleResponse {

    private String status;

    private List<List<Object>> data;

    public List<BithumbCandle> toCryptoCandleList(String symbol) {
        return Optional.ofNullable(data)
                .orElse(Collections.emptyList())
                .stream()
                .map(candle -> toBithumbCandle(symbol, candle))
                .collect(Collectors.toList());
    }

    private BithumbCandle toBithumbCandle(String symbol, List<Object> candle) {
        return new BithumbCandle(symbol, (Long) candle.get(0), (String) candle.get(1), (String) candle.get(2), (String) candle.get(3), (String) candle.get(4), (String) candle.get(5));
    }

}
