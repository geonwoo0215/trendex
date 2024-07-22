package com.trendex.trendex.domain.candle;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public enum CandleAnalysisTime {

    Volume_START_TIME_STAMP(LocalDateTime.now().minusMinutes(11L).toEpochSecond(ZoneOffset.UTC) * 1000),
    Volume_END_TIME_STAMP(LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000),
    MACD_TWELVE_TIME_STAMP(LocalDateTime.now().minusDays(12L).toEpochSecond(ZoneOffset.UTC) * 1000),
    MACD_NINE_TIME_STAMP(LocalDateTime.now().minusDays(9L).toEpochSecond(ZoneOffset.UTC) * 1000),
    MACD_TWENTY_SIX_TIME_STAMP(LocalDateTime.now().minusDays(26L).toEpochSecond(ZoneOffset.UTC) * 1000),
    RSI_FOURTEEN_TIME_STAMP(LocalDateTime.now().minusDays(14L).toEpochSecond(ZoneOffset.UTC) * 1000);

    private final long time;

    CandleAnalysisTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
