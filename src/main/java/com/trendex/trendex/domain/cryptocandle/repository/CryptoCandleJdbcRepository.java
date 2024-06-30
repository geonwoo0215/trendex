package com.trendex.trendex.domain.cryptocandle.repository;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CryptoCandleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CryptoCandleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<CryptoCandle> cryptoCandles) {

        String sql = "INSERT INTO crypto_candle (exchange_name, symbol, volume, timestamp, open_price, high_price, low_price, close_price, quote_volume) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CryptoCandle candle = cryptoCandles.get(i);
                ps.setString(1, candle.getExchangeName());
                ps.setString(2, candle.getSymbol());
                ps.setDouble(3, candle.getVolume());
                ps.setObject(4, candle.getTimestamp());
                ps.setDouble(5, candle.getOpenPrice());
                ps.setDouble(6, candle.getHighPrice());
                ps.setDouble(7, candle.getLowPrice());
                ps.setDouble(8, candle.getClosePrice());
                ps.setObject(9, candle.getQuoteVolume());
            }

            @Override
            public int getBatchSize() {
                return cryptoCandles.size();
            }
        });
    }
}
