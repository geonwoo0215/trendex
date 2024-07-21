package com.trendex.trendex.domain.macd.upbitmacd.model;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "idx_upbit_macd_market_timestamp", columnList = "market, timestamp")
)
public class UpbitMacd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double macdValue;

    private Double macdSignalValue;

    private boolean signalHigherThanMacd;

    private Long timestamp;

    public UpbitMacd(String market, Double macdValue, Double macdSignalValue, Long timestamp) {
        this.market = market;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
        this.signalHigherThanMacd = macdSignalValue > macdValue;
        this.timestamp = timestamp;
    }

    public static UpbitMacd of(String symbol, List<Double> closePriceValues26, List<Double> closePriceValues12, List<Double> macdValues) {

        if (closePriceValues26.size() < CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime()) {
            return null;
        }

        Double macdValue = CandleAnalysisUtil.calculateMACD(closePriceValues26, closePriceValues12);
        Double macdSignalValue = null;

        if (macdValues.size() >= 9) {
            macdSignalValue = CandleAnalysisUtil.calculateMACDSignal(macdValues);
        }

        Long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;
        return new UpbitMacd(symbol, macdValue, macdSignalValue, timestamp);
    }

}
