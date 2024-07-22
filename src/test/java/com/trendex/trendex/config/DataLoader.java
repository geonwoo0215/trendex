package com.trendex.trendex.config;

import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class DataLoader {

    private JdbcTemplate jdbcTemplate;

    public DataLoader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void batchUpbitCandleInsert(List<UpbitCandle> upbitCandles) {
        String sql = "INSERT INTO upbit_candle (" +
                "market, candle_date_time_utc, candle_date_time_kst, opening_price, high_price, low_price, trade_price, " +
                "timestamp, candle_acc_trade_price, volume, unit" +
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
                ps.setDouble(7, candle.getClosePrice());
                ps.setLong(8, candle.getTimestamp());
                ps.setDouble(9, candle.getCandleAccTradePrice());
                ps.setString(10, candle.getVolume());
                ps.setInt(11, candle.getUnit());
            }

            @Override
            public int getBatchSize() {
                return upbitCandles.size();
            }
        });
    }

    public void batchUpbitRsiInsert(List<UpbitRsi> upbitRsis) {
        String sql = "INSERT INTO upbit_rsi (" +
                "market, value, timestamp" +
                ") VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitRsi upbitRsi = upbitRsis.get(i);
                ps.setString(1, upbitRsi.getMarket());
                ps.setDouble(2, upbitRsi.getValue());
                ps.setLong(3, upbitRsi.getTimestamp());
            }

            @Override
            public int getBatchSize() {
                return upbitRsis.size();
            }
        });
    }

}
