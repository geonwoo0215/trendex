package com.trendex.trendex.domain.orderbook.binanceorderbook.repository;

import com.trendex.trendex.domain.orderbook.binanceorderbook.model.BinanceOrderBook;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BinanceOrderBookJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceOrderBookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceOrderBook> binanceOrderBooks) {
        String sql = "INSERT INTO binance_order_book (symbol, ask_bid, price, qty) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceOrderBook orderBook = binanceOrderBooks.get(i);
                ps.setString(1, orderBook.getSymbol());
                ps.setString(2, orderBook.getAskBid());
                ps.setDouble(3, orderBook.getPrice());
                ps.setDouble(4, orderBook.getQty());
            }

            @Override
            public int getBatchSize() {
                return binanceOrderBooks.size();
            }
        });
    }
}
