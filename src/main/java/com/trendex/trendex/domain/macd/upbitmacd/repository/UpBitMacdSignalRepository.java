package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacdSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpBitMacdSignalRepository extends JpaRepository<UpbitMacdSignal, Long> {

    @Query("SELECT u FROM UpbitMacdSignal u JOIN u.upbitMacd m ORDER BY m.timestamp DESC")
    List<UpbitMacdSignal> findLatest();

}
