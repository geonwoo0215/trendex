package com.trendex.trendex.domain.rsi.upbitrsi.repository;

import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UpbitRsiRepository extends JpaRepository<UpbitRsi, Long> {

    @Query("SELECT ur " +
            "FROM UpbitRsi ur " +
            "WHERE ur.market = :market " +
            "ORDER BY ur.timestamp DESC")
    Optional<UpbitRsi> findLatest(@Param("market") String market);

    @Query("SELECT ur " +
            "FROM UpbitRsi ur " +
            "WHERE ur.market " +
            "IN :markets " +
            "AND ur.timestamp = (" +
            "SELECT MAX(u.timestamp) " +
            "FROM UpbitRsi u " +
            "WHERE u.market = ur.market)")
    List<UpbitRsi> findLatestForMarkets(@Param("markets") List<String> markets);


}
