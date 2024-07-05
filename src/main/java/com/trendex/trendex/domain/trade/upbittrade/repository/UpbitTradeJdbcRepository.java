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
        String sql = "INSERT INTO upbit_trade (id, market, trade_date_utc, trade_time_utc, timestamp, trade_price, trade_volume, prev_closing_price, change_price, ask_bid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitTrade upbitTrade = upbitTrades.get(i);
                ps.setLong(1, upbitTrade.getId());
                ps.setString(2, upbitTrade.getMarket());
                ps.setString(3, upbitTrade.getTradeDateUtc());
                ps.setString(4, upbitTrade.getTradeTimeUtc());
                ps.setLong(5, upbitTrade.getTimestamp());
                ps.setLong(6, upbitTrade.getTradePrice());
                ps.setDouble(7, upbitTrade.getTradeVolume());
                ps.setLong(8, upbitTrade.getPrevClosingPrice());
                ps.setLong(9, upbitTrade.getChangePrice());
                ps.setString(10, upbitTrade.getAskBid());
            }

            @Override
            public int getBatchSize() {
                return upbitTrades.size();
            }
        });
    }
}