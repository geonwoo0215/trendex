package com.trendex.trendex.domain.upbitmarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitMarket {

    @Id
    private String market;

    public UpbitMarket(String market) {
        this.market = market;
    }
}
