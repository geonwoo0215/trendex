package com.trendex.trendex.domain.candle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleAnalysisService {

    private static final double SPIKE_VALUE = 10.0;

    private static final int RSI_PERIOD = 14;

    private static final int MACD_TWELVE = 12;

    private static final int MACD_TWENTY_SIX = 26;

    private static final int MACD_NINE = 9;

    public double calculateRSI(List<CryptoClosePrice> prices) {
        if (prices == null || prices.size() <= RSI_PERIOD) {
            throw new IllegalArgumentException("Not enough data points to calculate RSI");
        }

        double emaGain = 0;
        double emaLoss = 0;
        double prevPrice = prices.get(0).getTradePrice();
        double initialGain = 0;
        double initialLoss = 0;

        double multiplier = 2.0 / (RSI_PERIOD + 1);

        for (int i = 1; i <= RSI_PERIOD; i++) {
            double price = prices.get(i).getTradePrice();
            double change = price - prevPrice;
            prevPrice = price;

            if (change > 0) {
                initialGain += change;
            } else {
                initialLoss -= change;
            }
        }

        double avgGain = initialGain / RSI_PERIOD;
        double avgLoss = initialLoss / RSI_PERIOD;
        emaGain = avgGain;
        emaLoss = avgLoss;

        for (int i = RSI_PERIOD + 1; i < prices.size(); i++) {
            double price = prices.get(i).getTradePrice();
            double change = price - prevPrice;
            prevPrice = price;

            double gain = change > 0 ? change : 0;
            double loss = change < 0 ? -change : 0;

            emaGain = (gain * multiplier) + (emaGain * (1 - multiplier));
            emaLoss = (loss * multiplier) + (emaLoss * (1 - multiplier));
        }

        double rs = emaGain / emaLoss;
        double rsi = 100 - (100 / (1 + rs));
        return rsi;
    }

    public boolean isVolumeSpike(List<Double> upbitCandleMappings, double currentVolume) {

        double average = upbitCandleMappings
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        if (average == 0 && currentVolume > 0) {
            return true;
        }

        return currentVolume >= average * SPIKE_VALUE;

    }

    public double calculateEMA(List<Double> prices, int period) {
        double ema = prices.get(0);
        double multiplier = 2.0 / (period + 1);

        for (int i = 1; i < prices.size(); i++) {
            ema = (prices.get(i) * multiplier + ema * (1 - multiplier));
        }

        return ema;
    }

    public double calculateMACD(List<Double> cryptoClosePrices26, List<Double> cryptoClosePrices12) {

        double ema26 = calculateEMA(cryptoClosePrices26, MACD_TWENTY_SIX);
        double ema12 = calculateEMA(cryptoClosePrices12, MACD_TWELVE);

        return ema12 - ema26;
    }

    public double calculateMACDSignal(List<Double> macd) {

        return calculateEMA(macd, MACD_NINE);
    }

    public Decision decideByMacd(double macd, double macdSignal, boolean higherThanMacd) {
        if (higherThanMacd) {
            if (macd > macdSignal) {
                return Decision.BUY;
            }
        } else {
            if (macd < macdSignal) {
                return Decision.SELL;
            }
        }
        return Decision.NOTHING;

    }


}
