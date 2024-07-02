package com.trendex.trendex.domain.candle.upbitcandle.repository;

import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitCandleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitCandleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitCandle> upbitCandles) {
        String sql = "INSERT INTO upbit_candle (" +
                "market, candle_date_time_utc, candle_date_time_kst, opening_price, high_price, low_price, trade_price, " +
                "timestamp, candle_acc_trade_price, candle_acc_trade_volume, unit" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitCandle candle = upbitCandles.get(i);
                ps.setString(1, candle.getMarket());
                ps.setObject(2, candle.getCandleDateTimeUtc());
                ps.setObject(3, candle.getCandleDateTimeKst());
                ps.setDouble(4, candle.getOpeningPrice());
                ps.setDouble(5, candle.getHighPrice());
                ps.setDouble(6, candle.getLowPrice());
                ps.setDouble(7, candle.getTradePrice());
                ps.setLong(8, candle.getTimestamp());
                ps.setDouble(9, candle.getCandleAccTradePrice());
                ps.setDouble(10, candle.getCandleAccTradeVolume());
                ps.setInt(11, candle.getUnit());
            }

            @Override
            public int getBatchSize() {
                return upbitCandles.size();
            }
        });
    }
}
