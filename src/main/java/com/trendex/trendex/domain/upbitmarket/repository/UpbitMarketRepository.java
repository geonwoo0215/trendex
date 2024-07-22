package com.trendex.trendex.domain.upbitmarket.repository;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpbitMarketRepository extends JpaRepository<UpbitMarket, String> {

    @Query("SELECT um " +
            "FROM UpbitMarket um " +
            "WHERE um.market like 'KRW%' ")
    List<UpbitMarket> findMarketsStartWithKRW();


}
