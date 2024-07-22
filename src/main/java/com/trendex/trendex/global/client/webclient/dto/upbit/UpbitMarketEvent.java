package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpbitMarketEvent {

    private boolean warning;

    @JsonProperty("caution")
    private UpbitMarketCaution caution;

}
