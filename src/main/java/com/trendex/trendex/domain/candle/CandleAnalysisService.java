package com.trendex.trendex.domain.candle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleAnalysisService {

    private static final double SPIKE_VALUE = 10.0;

    private static final int RSI_PERIOD = 14;

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

    public boolean isVolumeSpike(List<CryptoVolume> upbitCandleMappings, double currentVolume) {

        double average = upbitCandleMappings.stream()
                .mapToDouble(cryptoVolume -> Double.parseDouble(cryptoVolume.getVolume()))
                .average()
                .orElse(0.0);

        if (average == 0 && currentVolume > 0) {
            return true;
        }

        return currentVolume >= average * SPIKE_VALUE;

    }

}
