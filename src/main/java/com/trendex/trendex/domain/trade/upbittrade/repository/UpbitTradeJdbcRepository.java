package com.trendex.trendex.domain.trade.upbittrade.repository;

import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitTradeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitTradeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitTrade> upbitTrades) {
        String sql = "INSERT INTO upbit_trade (market, trade_date_utc, trade_time_utc, timestamp, trade_price, trade_volume, prev_closing_price, change_price, ask_bid, sequential_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitTrade upbitTrade = upbitTrades.get(i);
                ps.setString(1, upbitTrade.getMarket());
                ps.setString(2, upbitTrade.getTradeDateUtc());
                ps.setString(3, upbitTrade.getTradeTimeUtc());
                ps.setLong(4, upbitTrade.getTimestamp());
                ps.setLong(5, upbitTrade.getTradePrice());
                ps.setDouble(6, upbitTrade.getTradeVolume());
                ps.setLong(7, upbitTrade.getPrevClosingPrice());
                ps.setLong(8, upbitTrade.getChangePrice());
                ps.setString(9, upbitTrade.getAskBid());
                ps.setLong(10, upbitTrade.getSequentialId());
            }

            @Override
            public int getBatchSize() {
                return upbitTrades.size();
            }
        });
    }
}
