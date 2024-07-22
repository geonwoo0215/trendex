package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitMacdJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitMacdJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitMacd> upbitMacds) {
        String sql = "INSERT INTO upbit_macd (market, macd_value, macd_signal_value, signal_higher_than_macd, timestamp) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitMacd upbitMacd = upbitMacds.get(i);
                ps.setString(1, upbitMacd.getMarket());
                ps.setDouble(2, upbitMacd.getMacdValue());
                ps.setDouble(3, upbitMacd.getMacdSignalValue());
                ps.setBoolean(4, upbitMacd.isSignalHigherThanMacd());
                ps.setLong(5, upbitMacd.getTimestamp());
            }

            @Override
            public int getBatchSize() {
                return upbitMacds.size();
            }
        });
    }

}
