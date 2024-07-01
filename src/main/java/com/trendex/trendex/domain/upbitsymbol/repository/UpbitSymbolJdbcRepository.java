package com.trendex.trendex.domain.upbitsymbol.repository;

import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UpbitSymbolJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpbitSymbolJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<UpbitSymbol> upbitSymbols) {

        String sql = "INSERT INTO upbit_symbol (symbol) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UpbitSymbol upbitSymbol = upbitSymbols.get(i);
                ps.setString(1, upbitSymbol.getSymbol());
            }

            @Override
            public int getBatchSize() {
                return upbitSymbols.size();
            }
        });
    }
}
