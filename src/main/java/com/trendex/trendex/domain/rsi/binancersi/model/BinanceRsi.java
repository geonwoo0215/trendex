package com.trendex.trendex.domain.rsi.binancersi.model;

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
        indexes = @Index(name = "idx_binance_rsi_symbol_timestamp", columnList = "symbol, timestamp")
)
public class BinanceRsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Double value;

    private Long timestamp;

    public BinanceRsi(String symbol, Double value, Long timestamp) {
        this.symbol = symbol;
        this.value = value;
        this.timestamp = timestamp;
    }

    public static BinanceRsi of(String symbol, List<Double> cryptoClosePrices14) {
        if (cryptoClosePrices14.size() < 14) {
            return null;
        }
        Double rsiValue = CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
        return new BinanceRsi(symbol, rsiValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
    }

}
