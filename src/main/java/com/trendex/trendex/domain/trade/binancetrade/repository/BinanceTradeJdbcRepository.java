package com.trendex.trendex.domain.trade.binancetrade.repository;

import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BinanceTradeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceTradeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceTrade> binanceTrades) {
        String sql = "INSERT INTO binance_trade (id, price, qty, quote_qty, timestamp, is_buyer_maker, is_best_match) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceTrade trade = binanceTrades.get(i);
                ps.setString(1, trade.getId());
                ps.setString(2, trade.getPrice());
                ps.setString(3, trade.getQty());
                ps.setString(4, trade.getQuoteQty());
                ps.setLong(5, trade.getTimestamp());
                ps.setBoolean(6, trade.isBuyerMaker());
                ps.setBoolean(7, trade.isBestMatch());
            }

            @Override
            public int getBatchSize() {
                return binanceTrades.size();
            }
        });
    }
}
