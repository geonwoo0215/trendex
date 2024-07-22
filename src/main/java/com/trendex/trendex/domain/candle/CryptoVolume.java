package com.trendex.trendex.domain.candle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoVolume {

    private String volume;

    public CryptoVolume(String volume) {
        this.volume = volume;
    }
}
