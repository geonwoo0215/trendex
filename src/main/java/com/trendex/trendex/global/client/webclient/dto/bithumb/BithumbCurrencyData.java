package com.trendex.trendex.global.client.webclient.dto.bithumb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BithumbCurrencyData {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("net_type")
    private String netType;

    @JsonProperty("minimum")
    private String minimum;

}
