package com.trendex.trendex.domain.macd.binancemacd.model;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_binance_macd_symbol_timestamp", columnList = "symbol, timestamp")
)
public class BinanceMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double macdValue;

    private Double macdSignalValue;

    private Boolean signalHigherThanMacd;

    private Long timestamp;

    public BinanceMacd(String symbol, Double macdValue, Double macdSignalValue, Long timestamp) {
        this.symbol = symbol;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
        this.signalHigherThanMacd = Objects.isNull(macdSignalValue) ? null : macdSignalValue > macdValue;
        this.timestamp = timestamp;
    }

    public static BinanceMacd of(String symbol, List<Double> closePriceValues26, List<Double> closePriceValues12, List<Double> macdValues) {

        if (closePriceValues26.size() < CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime()) {
            return null;
        }

        Double macdValue = CandleAnalysisUtil.calculateMACD(closePriceValues26, closePriceValues12);
        Double macdSignalValue = null;

        if (macdValues.size() >= 9) {
            macdSignalValue = CandleAnalysisUtil.calculateMACDSignal(macdValues);
        }

        Long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;
        return new BinanceMacd(symbol, macdValue, macdSignalValue, timestamp);
    }

}
