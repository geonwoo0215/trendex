package com.trendex.trendex.domain.trade.bithumbtrade.repository;

import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BithumbTradeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BithumbTradeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BithumbTrade> bithumbTrades) {
        String sql = "INSERT INTO bithumb_trade (symbol, transaction_date, type, units_traded, price, total) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BithumbTrade trade = bithumbTrades.get(i);
                ps.setString(1, trade.getSymbol());
                ps.setString(2, trade.getTransactionDate());
                ps.setString(3, trade.getType());
                ps.setString(4, trade.getUnitsTraded());
                ps.setString(5, trade.getPrice());
                ps.setString(6, trade.getTotal());
            }

            @Override
            public int getBatchSize() {
                return bithumbTrades.size();
            }
        });
    }
}
