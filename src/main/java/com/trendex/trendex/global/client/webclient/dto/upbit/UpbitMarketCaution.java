package com.trendex.trendex.global.client.webclient.dto.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpbitMarketCaution {

    @JsonProperty("PRICE_FLUCTUATIONS")
    private boolean priceFluctuations;

    @JsonProperty("TRADING_VOLUME_SOARING")
    private boolean tradingVolumeSoaring;

    @JsonProperty("DEPOSIT_AMOUNT_SOARING")
    private boolean depositAmountSoaring;

    @JsonProperty("GLOBAL_PRICE_DIFFERENCES")
    private boolean globalPriceDifferences;

    @JsonProperty("CONCENTRATION_OF_SMALL_ACCOUNTS")
    private boolean concentrationOfSmallAccounts;

}
