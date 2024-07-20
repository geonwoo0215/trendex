package com.trendex.trendex.domain.macd.binancemacd.repository;

import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class BinanceMacdJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceMacdJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceMacd> binanceMacds) {
        String sql = "INSERT INTO binance_macd (symbol, macd_value, macd_signal_value, signal_higher_than_macd, timestamp) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceMacd binanceMacd = binanceMacds.get(i);
                ps.setString(1, binanceMacd.getSymbol());
                ps.setDouble(2, binanceMacd.getMacdValue());
                if (binanceMacd.getMacdSignalValue() != null) {
                    ps.setDouble(3, binanceMacd.getMacdSignalValue());
                } else {
                    ps.setNull(3, Types.DOUBLE);
                }

                if (binanceMacd.getSignalHigherThanMacd() != null) {
                    ps.setBoolean(4, binanceMacd.getSignalHigherThanMacd());
                } else {
                    ps.setNull(4, Types.BOOLEAN);
                }
                ps.setLong(5, binanceMacd.getTimestamp());
            }

            @Override
            public int getBatchSize() {
                return binanceMacds.size();
            }
        });
    }
}
