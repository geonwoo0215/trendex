package com.trendex.trendex.domain.rsi.upbitrsi.repository;

import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitRsiJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitRsiJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitRsi> upbitRsis) {
        String sql = "INSERT INTO upbit_rsi (market, value, timestamp) VALUES (?, ?, ?)";

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
