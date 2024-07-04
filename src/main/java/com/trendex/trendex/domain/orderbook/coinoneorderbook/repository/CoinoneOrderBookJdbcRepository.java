package com.trendex.trendex.domain.orderbook.coinoneorderbook.repository;

import com.trendex.trendex.domain.orderbook.coinoneorderbook.model.CoinoneOrderBook;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoinoneOrderBookJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoinoneOrderBookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<CoinoneOrderBook> coinoneOrderBooks) {
        String sql = "INSERT INTO coinone_order_book (id, ask_bid, target_currency, order_book_unit, price, qty) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CoinoneOrderBook orderBook = coinoneOrderBooks.get(i);
                ps.setString(1, orderBook.getId());
                ps.setString(2, orderBook.getAskBid());
                ps.setString(3, orderBook.getTargetCurrency());
                ps.setString(4, orderBook.getOrderBookUnit());
                ps.setDouble(5, orderBook.getPrice());
                ps.setDouble(6, orderBook.getQty());
            }

            @Override
            public int getBatchSize() {
                return coinoneOrderBooks.size();
            }
        });
    }

}
