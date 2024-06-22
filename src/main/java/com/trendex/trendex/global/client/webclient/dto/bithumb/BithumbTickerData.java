package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.Map;
@Getter
public class BithumbTickerData {

    private Map<String, BithumbTickerDetail> tickerDetails;

    private String date;

}
