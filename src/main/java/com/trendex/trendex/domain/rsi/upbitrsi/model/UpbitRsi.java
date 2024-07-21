package com.trendex.trendex.domain.rsi.upbitrsi.model;

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
        indexes = @Index(name = "idx_upbit_rsi_market_timestamp", columnList = "market, timestamp")
)
public class UpbitRsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private Double value;

    private Long timestamp;

    public UpbitRsi(String market, Double value, Long timestamp) {
        this.market = market;
        this.value = value;
        this.timestamp = timestamp;
    }

    public static UpbitRsi of(String market, List<Double> cryptoClosePrices14) {
        if (cryptoClosePrices14.size() < 14) {
            return null;
        }
        Double rsiValue = CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
        return new UpbitRsi(market, rsiValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
    }


}
