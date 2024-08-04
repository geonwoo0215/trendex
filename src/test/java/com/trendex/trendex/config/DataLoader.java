package com.trendex.trendex.config;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBookUnit;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
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

    public void batchUpbitOrderBookInsert(List<UpbitOrderBook> upbitOrderBooks) {

        for (UpbitOrderBook orderBook : upbitOrderBooks) {
            Long orderBookId = insertOrderBookAndGetId(orderBook);

            insertOrderBookUnits(orderBook.getUpbitOrderBookUnits(), orderBookId);
        }
    }

    private Long insertOrderBookAndGetId(UpbitOrderBook orderBook) {
        String sql = "INSERT INTO upbit_order_book (market, timestamp, total_ask_size, total_bid_size) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, orderBook.getMarket());
                ps.setLong(2, orderBook.getTimestamp());
                ps.setDouble(3, orderBook.getTotalAskSize());
                ps.setDouble(4, orderBook.getTotalBidSize());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    private void insertOrderBookUnits(List<UpbitOrderBookUnit> units, Long orderBookId) {
        String sql = "INSERT INTO upbit_order_book_unit (ask_price, bid_price, ask_size, bid_size, upbit_order_book_id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitOrderBookUnit unit = units.get(i);
                ps.setLong(1, unit.getAskPrice());
                ps.setLong(2, unit.getBidPrice());
                ps.setDouble(3, unit.getAskSize());
                ps.setDouble(4, unit.getBidSize());
                ps.setLong(5, orderBookId); // Associate with the UpbitOrderBook ID
            }

            @Override
            public int getBatchSize() {
                return units.size();
            }
        });
    }

    public void batchUpbitTradeInsert(List<UpbitTrade> upbitTrades) {
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
