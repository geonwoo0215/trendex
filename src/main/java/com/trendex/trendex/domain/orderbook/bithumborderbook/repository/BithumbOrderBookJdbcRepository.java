package com.trendex.trendex.domain.orderbook.bithumborderbook.repository;

import com.trendex.trendex.domain.orderbook.bithumborderbook.model.BithumbOrderBook;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BithumbOrderBookJdbcRepository {


    private final JdbcTemplate jdbcTemplate;

    public BithumbOrderBookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BithumbOrderBook> bithumbOrderBooks) {
        String sql = "INSERT INTO bithumb_order_book (symbol, ask_bid, price, quantity) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BithumbOrderBook orderBook = bithumbOrderBooks.get(i);
                ps.setString(1, orderBook.getSymbol());
                ps.setString(2, orderBook.getAskBid());
                ps.setDouble(3, orderBook.getPrice());
                ps.setDouble(4, orderBook.getQuantity());
            }

            @Override
            public int getBatchSize() {
                return bithumbOrderBooks.size();
            }
        });
    }

}
