package com.trendex.trendex.domain.binancesymbol.fixture;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;

import java.util.List;
import java.util.stream.Collectors;

public class BinanceSymbolFixture {

    private static final String SYMBOL = "BTC";

    public static BinanceSymbol createBinanceSymbol() {
        return new BinanceSymbol(SYMBOL);
    }

    public static BinanceSymbol createBinanceSymbol(String symbol) {
        return new BinanceSymbol(symbol);
    }

    public static List<BinanceSymbol> createBinanceSymbols(List<String> symbols) {

        return symbols.stream()
                .map(BinanceSymbolFixture::createBinanceSymbol)
                .collect(Collectors.toList());

    }

}
