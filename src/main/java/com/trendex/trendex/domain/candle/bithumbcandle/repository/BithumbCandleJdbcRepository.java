package com.trendex.trendex.domain.candle.bithumbcandle.repository;

import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BithumbCandleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BithumbCandleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BithumbCandle> bithumbCandles) {
        String sql = "INSERT INTO bithumb_candle (" +
                "symbol, timestamp, open_price, high_price, low_price, close_price, volume" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BithumbCandle candle = bithumbCandles.get(i);
                ps.setString(1, candle.getSymbol());
                ps.setLong(2, candle.getTimestamp());
                ps.setString(3, candle.getOpenPrice());
                ps.setString(4, candle.getHighPrice());
                ps.setString(5, candle.getLowPrice());
                ps.setString(6, candle.getClosePrice());
                ps.setString(7, candle.getVolume());
            }

            @Override
            public int getBatchSize() {
                return bithumbCandles.size();
            }
        });
    }
}
