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

    @Query(value = "SELECT ur1.id, ur1.value, ur1.market, ur1.timestamp " +
            "FROM upbit_rsi ur1 " +
            "INNER JOIN ( " +
            "    SELECT market, MAX(timestamp) AS max_timestamp " +
            "    FROM upbit_rsi " +
            "    WHERE market IN :markets " +
            "    GROUP BY market " +
            ") ur2 ON ur1.market = ur2.market AND ur1.timestamp = ur2.max_timestamp",
            nativeQuery = true)
    List<UpbitRsi> findLatestForMarkets(@Param("markets") List<String> markets);


}
