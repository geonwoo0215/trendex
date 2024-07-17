package com.trendex.trendex.domain.upbitmarket.repository;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitMarketJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitMarketJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitMarket> upbitMarkets) {

        String sql = "INSERT INTO upbit_market (market) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitMarket upbitSymbol = upbitMarkets.get(i);
                ps.setString(1, upbitSymbol.getMarket());
            }

            @Override
            public int getBatchSize() {
                return upbitMarkets.size();
            }
        });
    }
}
