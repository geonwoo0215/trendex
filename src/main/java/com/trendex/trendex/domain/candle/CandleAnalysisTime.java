package com.trendex.trendex.domain.candle;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public enum CandleAnalysisTime {

    STAR_TIME_STAMP(LocalDateTime.now().minusMinutes(11L).toEpochSecond(ZoneOffset.UTC) * 1000),
    END_TIME_STAMP(LocalDateTime.now().minusMinutes(2L).toEpochSecond(ZoneOffset.UTC) * 1000);

    private final long time;

    CandleAnalysisTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
