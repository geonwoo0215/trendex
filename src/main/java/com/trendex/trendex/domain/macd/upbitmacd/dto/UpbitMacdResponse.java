package com.trendex.trendex.domain.macd.upbitmacd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Macd DTO")
@Getter
public class UpbitMacdResponse {

    @Schema(description = "거래 시장")
    private String market;

    @Schema(description = "MACD 결정에 의한 매매 결정")
    private String decision;

    @Schema(description = "MACD 값")
    private Double macdValue;

    @Schema(description = "MACD 신호선 값")
    private Double macdSignalValue;

    public UpbitMacdResponse(String market, String decision, Double macdValue, Double macdSignalValue) {
        this.market = market;
        this.decision = decision;
        this.macdValue = macdValue;
        this.macdSignalValue = macdSignalValue;
    }
}
