package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.List;

@Getter
public class BithumbWithdrawMinimum {

    private String status;

    private List<BithumbCurrencyData> data;

}
