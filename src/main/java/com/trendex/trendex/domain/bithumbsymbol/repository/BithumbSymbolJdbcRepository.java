package com.trendex.trendex.domain.bithumbsymbol.repository;

import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BithumbSymbolJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BithumbSymbolJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BithumbSymbol> bithumbSymbols) {

        String sql = "INSERT INTO bithumb_symbol (symbol) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BithumbSymbol bithumbSymbol = bithumbSymbols.get(i);
                ps.setString(1, bithumbSymbol.getSymbol());
            }

            @Override
            public int getBatchSize() {
                return bithumbSymbols.size();
            }
        });
    }
}
