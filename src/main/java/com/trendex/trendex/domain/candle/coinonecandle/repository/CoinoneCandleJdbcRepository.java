package com.trendex.trendex.domain.candle.coinonecandle.repository;

import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoinoneCandleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoinoneCandleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<CoinoneCandle> coinoneCandles) {
        String sql = "INSERT INTO coinone_candle (" +
                "symbol, timestamp, open, high, low, close, target_volume, quote_volume" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CoinoneCandle candle = coinoneCandles.get(i);
                ps.setString(1, candle.getSymbol());
                ps.setLong(2, candle.getTimestamp());
                ps.setString(3, candle.getOpen());
                ps.setString(4, candle.getHigh());
                ps.setString(5, candle.getLow());
                ps.setString(6, candle.getClose());
                ps.setString(7, candle.getTargetVolume());
                ps.setString(8, candle.getVolume());
            }

            @Override
            public int getBatchSize() {
                return coinoneCandles.size();
            }
        });
    }
}
