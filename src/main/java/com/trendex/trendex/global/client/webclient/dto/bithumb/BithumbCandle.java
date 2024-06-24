package com.trendex.trendex.global.client.webclient.dto.bithumb;

import lombok.Getter;

import java.util.List;

@Getter
public class BithumbCandle {

    private String status;

    private List<List<Object>> data;

}
