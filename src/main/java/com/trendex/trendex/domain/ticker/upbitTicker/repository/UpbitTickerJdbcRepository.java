package com.trendex.trendex.domain.ticker.upbitTicker.repository;

import com.trendex.trendex.domain.ticker.upbitTicker.model.UpbitTicker;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitTickerJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitTickerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitTicker> upbitTickers) {
        String sql = "INSERT INTO upbit_ticker (" +
                "market, trade_date, trade_time, trade_date_kst, trade_time_kst, trade_timestamp, " +
                "opening_price, high_price, low_price, trade_price, prev_closing_price, change, " +
                "change_price, change_rate, signed_change_price, signed_change_rate, trade_volume, " +
                "acc_trade_price, acc_trade_price_24h, acc_trade_volume, acc_trade_volume_24h, " +
                "highest_52_week_price, highest_52_week_date, lowest_52_week_price, lowest_52_week_date, " +
                "timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitTicker ticker = upbitTickers.get(i);
                ps.setString(1, ticker.getMarket());
                ps.setString(2, ticker.getTradeDate());
                ps.setString(3, ticker.getTradeTime());
                ps.setString(4, ticker.getTradeDateKst());
                ps.setString(5, ticker.getTradeTimeKst());
                ps.setLong(6, ticker.getTradeTimestamp());
                ps.setLong(7, ticker.getOpeningPrice());
                ps.setLong(8, ticker.getHighPrice());
                ps.setLong(9, ticker.getLowPrice());
                ps.setLong(10, ticker.getTradePrice());
                ps.setLong(11, ticker.getPrevClosingPrice());
                ps.setString(12, ticker.getChange());
                ps.setLong(13, ticker.getChangePrice());
                ps.setDouble(14, ticker.getChangeRate());
                ps.setLong(15, ticker.getSignedChangePrice());
                ps.setDouble(16, ticker.getSignedChangeRate());
                ps.setDouble(17, ticker.getTradeVolume());
                ps.setDouble(18, ticker.getAccTradePrice());
                ps.setDouble(19, ticker.getAccTradePrice24h());
                ps.setDouble(20, ticker.getAccTradeVolume());
                ps.setDouble(21, ticker.getAccTradeVolume24h());
                ps.setLong(22, ticker.getHighest52WeekPrice());
                ps.setString(23, ticker.getHighest52WeekDate());
                ps.setLong(24, ticker.getLowest52WeekPrice());
                ps.setString(25, ticker.getLowest52WeekDate());
                ps.setLong(26, ticker.getTimestamp());
            }

            @Override
            public int getBatchSize() {
                return upbitTickers.size();
            }
        });
    }
}