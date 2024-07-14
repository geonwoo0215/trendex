package com.trendex.trendex.domain.candle;

public enum Decision {

    SELL("매도 신호"),
    BUY("매수 신호"),
    NOTHING("신호 없음");

    private final String text;

    Decision(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
