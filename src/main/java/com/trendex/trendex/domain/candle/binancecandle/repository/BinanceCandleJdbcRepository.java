package com.trendex.trendex.domain.candle.binancecandle.repository;

import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BinanceCandleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceCandleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceCandle> binanceCandles) {
        String sql = "INSERT INTO binance_candle (" +
                "symbol, kline_open_time, open_price, high_price, low_price, close_price, volume, " +
                "kline_close_time, quote_asset_volume, number_of_trades, taker_buy_base_asset_volume, taker_buy_quote_asset_volume" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceCandle candle = binanceCandles.get(i);
                ps.setString(1, candle.getSymbol());
                ps.setLong(2, candle.getKlineOpenTime());
                ps.setString(3, candle.getOpenPrice());
                ps.setString(4, candle.getHighPrice());
                ps.setString(5, candle.getLowPrice());
                ps.setString(6, candle.getClosePrice());
                ps.setString(7, candle.getVolume());
                ps.setLong(8, candle.getKlineCloseTime());
                ps.setString(9, candle.getQuoteAssetVolume());
                ps.setInt(10, candle.getNumberOfTrades());
                ps.setString(11, candle.getTakerBuyBaseAssetVolume());
                ps.setString(12, candle.getTakerBuyQuoteAssetVolume());
            }

            @Override
            public int getBatchSize() {
                return binanceCandles.size();
            }
        });
    }
}
