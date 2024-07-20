package com.trendex.trendex.domain.rsi.binancersi.repository;

import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class BinanceRsiJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceRsiJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceRsi> binanceRsis) {
        String sql = "INSERT INTO binance_rsi (symbol, value, timestamp) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceRsi binanceRsi = binanceRsis.get(i);
                ps.setString(1, binanceRsi.getSymbol());
                ps.setDouble(2, binanceRsi.getValue());
                ps.setLong(3, binanceRsi.getTimestamp());
            }

            @Override
            public int getBatchSize() {
                return binanceRsis.size();
            }
        });
    }
}
