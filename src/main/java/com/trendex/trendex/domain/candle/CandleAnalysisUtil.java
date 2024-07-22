package com.trendex.trendex.domain.candle;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CandleAnalysisUtil {

    private static final double SPIKE_VALUE = 10.0;

    private static final int RSI_PERIOD = 14;

    private static final int MACD_TWELVE = 12;

    private static final int MACD_TWENTY_SIX = 26;

    private static final int MACD_NINE = 9;

    public static Double calculateRSI(List<Double> prices) {
        
        double prevPrice = prices.get(0);
        double initialGain = 0;
        double initialLoss = 0;

        double multiplier = 2.0 / (RSI_PERIOD + 1);

        for (int i = 1; i <= RSI_PERIOD; i++) {
            double price = prices.get(i);
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
        double emaGain = avgGain;
        double emaLoss = avgLoss;

        for (int i = RSI_PERIOD + 1; i < prices.size(); i++) {
            double price = prices.get(i);
            double change = price - prevPrice;
            prevPrice = price;

            double gain = change > 0 ? change : 0;
            double loss = change < 0 ? -change : 0;

            emaGain = (gain * multiplier) + (emaGain * (1 - multiplier));
            emaLoss = (loss * multiplier) + (emaLoss * (1 - multiplier));
        }

        if (emaLoss == 0) {
            return 100.0;
        }

        double rs = emaGain / emaLoss;
        double rsi = 100 - (100 / (1 + rs));
        return rsi;
    }

    public static boolean isVolumeSpike(List<Double> candleVolumes, double currentVolume) {

        double average = candleVolumes
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        if (average == 0 && currentVolume > 0) {
            return true;
        }

        return currentVolume >= average * SPIKE_VALUE;

    }

    public static double calculateEMA(List<Double> prices, int period) {
        double ema = prices.get(0);
        double multiplier = 2.0 / (period + 1);

        for (int i = 1; i < prices.size(); i++) {
            ema = (prices.get(i) * multiplier + ema * (1 - multiplier));
        }

        return ema;
    }

    public static double calculateMACD(List<Double> cryptoClosePrices26, List<Double> cryptoClosePrices12) {

        double ema26 = calculateEMA(cryptoClosePrices26, MACD_TWENTY_SIX);
        double ema12 = calculateEMA(cryptoClosePrices12, MACD_TWELVE);

        return ema12 - ema26;
    }

    public static double calculateMACDSignal(List<Double> macd) {

        return calculateEMA(macd, MACD_NINE);
    }

    public static Decision decideByMacd(double macd, double macdSignal, boolean higherThanMacd) {
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

    public static Decision decideByRsi(double rsi) {

        if (rsi > 70) {
            return Decision.SELL;
        } else if (rsi < 30) {
            return Decision.BUY;
        }

        return Decision.NOTHING;

    }


}
