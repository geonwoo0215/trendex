package com.trendex.trendex.domain.orderbook.upbitorderbook.repository;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitOrderBookJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitOrderBookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitOrderBook> upbitOrderBooks) {
        String sql = "INSERT INTO upbit_order_book (market, ask_price, bid_price, ask_size, bid_size) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitOrderBook orderBook = upbitOrderBooks.get(i);
                ps.setString(1, orderBook.getMarket());
                ps.setLong(2, orderBook.getAskPrice());
                ps.setLong(3, orderBook.getBidPrice());
                ps.setDouble(4, orderBook.getAskSize());
                ps.setDouble(5, orderBook.getBidSize());
            }

            @Override
            public int getBatchSize() {
                return upbitOrderBooks.size();
            }
        });
    }

}
