package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

@Getter
public class BithumbOrderBookResponse {

    private String status;

    private BithumbOrderBookIndividualData data;

}
