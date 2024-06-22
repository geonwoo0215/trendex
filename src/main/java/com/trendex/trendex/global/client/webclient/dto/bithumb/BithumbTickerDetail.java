package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

@Getter
public class BithumbTickerDetail {

    private String openingPrice;

    private String closingPrice;

    private String minPrice;

    private String maxPrice;

    private String unitsTraded;

    private String accTradeValue;

    private String prevTradeValue;

    private String unitsTraded24H;

    private String accTradeValue24H;

    private String fluctate24H;

    private String fluctateRate24H;

}
