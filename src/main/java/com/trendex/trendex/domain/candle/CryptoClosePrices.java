package com.trendex.trendex.domain.candle;

import java.util.List;
import java.util.stream.Collectors;

public class CryptoClosePrices {

    private List<CryptoClosePrice> cryptoClosePriceList;

    public CryptoClosePrices(List<CryptoClosePrice> cryptoClosePriceList) {
        this.cryptoClosePriceList = cryptoClosePriceList;
    }

    public List<Double> getClosePriceValues() {
        return cryptoClosePriceList.stream()
                .map(CryptoClosePrice::getClosePrice)
                .collect(Collectors.toList());
    }

    public List<CryptoClosePrice> getClosePriceValues(CandleAnalysisTime candleAnalysisTime) {
        return cryptoClosePriceList.stream()
                .filter(cryptoClosePrice -> cryptoClosePrice.getTimeStamp() <= candleAnalysisTime.getTime())
                .collect(Collectors.toList());
    }


}
