package com.trendex.trendex.domain.upbitmarket.fixture;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;

import java.util.List;
import java.util.stream.Collectors;

public class UpbitMarketFixture {

    private static final String MARKET = "KRW-BTC";

    public static UpbitMarket createUpbitMarket() {
        return new UpbitMarket(MARKET);
    }

    public static UpbitMarket createUpbitMarket(String market) {
        return new UpbitMarket(market);
    }

    public static List<UpbitMarket> createUpbitMarkets(List<String> markets) {

        return markets.stream()
                .map(UpbitMarketFixture::createUpbitMarket)
                .collect(Collectors.toList());

    }

}
