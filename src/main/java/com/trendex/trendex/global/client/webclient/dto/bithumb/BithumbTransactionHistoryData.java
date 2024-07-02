package com.trendex.trendex.global.client.webclient.dto.bithumb;

import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import lombok.Getter;

@Getter
public class BithumbTransactionHistoryData {

    private String transactionDate;

    private String type;

    private String unitsTraded;

    private String price;

    private String total;

    public BithumbTrade toBithumbTrade(String symbol) {
        return new BithumbTrade(symbol, transactionDate, type, unitsTraded, price, total);
    }
}
