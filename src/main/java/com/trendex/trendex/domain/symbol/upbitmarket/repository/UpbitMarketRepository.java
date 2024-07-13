package com.trendex.trendex.domain.symbol.upbitmarket.repository;

import com.trendex.trendex.domain.symbol.upbitmarket.model.UpbitMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpbitMarketRepository extends JpaRepository<UpbitMarket, Long> {

    @Query("SELECT u FROM UpbitMarket u WHERE u.market LIKE :prefix%")
    List<UpbitMarket> findMarketsStartingWith(@Param("prefix") String prefix);

}
