package com.trendex.trendex.domain.rsi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Rsi DTO")
@Getter
public class RsiResponse {

    @Schema(description = "거래 시장")
    private String market;

    @Schema(description = "Rsi 결정에 의한 매매 결정")
    private String decision;

    @Schema(description = "Rsi 값")
    private Double value;

    public RsiResponse(String market, String decision, Double value) {
        this.market = market;
        this.decision = decision;
        this.value = value;
    }
}
