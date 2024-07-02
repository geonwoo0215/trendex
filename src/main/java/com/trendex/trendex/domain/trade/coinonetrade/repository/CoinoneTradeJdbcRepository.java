package com.trendex.trendex.domain.trade.coinonetrade.repository;

import com.trendex.trendex.domain.trade.coinonetrade.model.CoinoneTrade;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoinoneTradeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoinoneTradeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<CoinoneTrade> coinoneTrades) {
        String sql = "INSERT INTO coinone_trade (id, symbol, timestamp, price, qty, is_seller_maker) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CoinoneTrade trade = coinoneTrades.get(i);
                ps.setString(1, trade.getId());
                ps.setString(2, trade.getSymbol());
                ps.setLong(3, trade.getTimestamp());
                ps.setString(4, trade.getPrice());
                ps.setString(5, trade.getQty());
                ps.setBoolean(6, trade.isSellerMaker());
            }

            @Override
            public int getBatchSize() {
                return coinoneTrades.size();
            }
        });
    }

}
