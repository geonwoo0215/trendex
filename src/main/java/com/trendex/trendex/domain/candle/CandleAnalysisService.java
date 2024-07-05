package com.trendex.trendex.domain.candle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleAnalysisService {

    private static final double SPIKE_VALUE = 10.0;

    public boolean isVolumeSpike(List<CryptoVolume> upbitCandleMappings, double currentVolume) {

        double average = upbitCandleMappings.stream()
                .mapToDouble(CryptoVolume::getVolume)
                .average()
                .orElse(0.0);

        if (average == 0 && currentVolume > 0) {
            return true;
        }

        return currentVolume >= average * SPIKE_VALUE;

    }

}
