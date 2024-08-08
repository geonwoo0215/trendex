package com.trendex.trendex.domain.trade.upbittrade.repository;

import com.trendex.trendex.domain.trade.upbittrade.dto.MarketAggregateDto;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpbitTradeRepository extends JpaRepository<UpbitTrade, Long> {

    @Query(value = "SELECT " +
            "    t.market, " +
            "    t.tradeTime, " +
            "    t.avgTradePrice, " +
            "    t.totalTradeVolume, " +
            "    o.avgAskPrice, " +
            "    o.avgBidPrice, " +
            "    o.totalAskSize, " +
            "    o.totalBidSize " +
            "FROM ( " +
            "    SELECT " +
            "        ut.market, " +
            "        date_trunc('hour', to_timestamp(ut.timestamp / 1000.0)) AS tradeTime, " +
            "        AVG(ut.trade_price) AS avgTradePrice, " +
            "        SUM(ut.trade_volume) AS totalTradeVolume " +
            "    FROM upbit_trade ut " +
            "    WHERE ut.timestamp BETWEEN :startTime AND :endTime " +
            "    GROUP BY ut.market, date_trunc('hour', to_timestamp(ut.timestamp / 1000.0)) " +
            ") t " +
            "INNER JOIN ( " +
            "    SELECT " +
            "        ob.market, " +
            "        date_trunc('hour', to_timestamp(ob.timestamp / 1000.0)) AS tradeTime, " +
            "        AVG(obu.ask_price) AS avgAskPrice, " +
            "        AVG(obu.bid_price) AS avgBidPrice, " +
            "        SUM(obu.ask_size) AS totalAskSize, " +
            "        SUM(obu.bid_size) AS totalBidSize " +
            "    FROM upbit_order_book ob " +
            "    JOIN upbit_order_book_unit obu ON ob.id = obu.upbit_order_book_id " +
            "    WHERE ob.timestamp BETWEEN :startTime AND :endTime " +
            "    GROUP BY ob.market, date_trunc('hour', to_timestamp(ob.timestamp / 1000.0)) " +
            ") o ON t.market = o.market AND t.tradeTime = o.tradeTime " +
            "ORDER BY t.market, t.tradeTime", nativeQuery = true)
    List<MarketAggregateDto> findAggregatedMarketData(
            @Param("startTime") Long startTime,
            @Param("endTime") Long endTime
    );
}
