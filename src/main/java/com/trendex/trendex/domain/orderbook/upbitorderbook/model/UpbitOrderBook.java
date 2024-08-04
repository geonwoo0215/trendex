package com.trendex.trendex.domain.orderbook.upbitorderbook.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpbitOrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String market;
    private Long timestamp;
    private Double totalAskSize;
    private Double totalBidSize;

    @OneToMany(mappedBy = "upbitOrderBook", cascade = CascadeType.ALL)
    private List<UpbitOrderBookUnit> upbitOrderBookUnits = new ArrayList<>();

    public UpbitOrderBook(String market, Long timestamp, Double totalAskSize, Double totalBidSize) {
        this.market = market;
        this.timestamp = timestamp;
        this.totalAskSize = totalAskSize;
        this.totalBidSize = totalBidSize;
    }

    public void addUpbitOrderBookUnits(UpbitOrderBookUnit upbitOrderBookUnits) {
        this.upbitOrderBookUnits.add(upbitOrderBookUnits);
        upbitOrderBookUnits.updateUpbitOrderBook(this);
    }

}
