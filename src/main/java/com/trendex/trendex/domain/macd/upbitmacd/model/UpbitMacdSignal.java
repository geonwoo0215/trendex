package com.trendex.trendex.domain.macd.upbitmacd.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitMacdSignal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;

    private boolean higherThanMacd;

    @OneToOne
    private UpbitMacd upbitMacd;

    public UpbitMacdSignal(double value, UpbitMacd upbitMacd) {
        this.value = value;
        this.upbitMacd = upbitMacd;
        this.higherThanMacd = value > upbitMacd.getValue();
    }

}
