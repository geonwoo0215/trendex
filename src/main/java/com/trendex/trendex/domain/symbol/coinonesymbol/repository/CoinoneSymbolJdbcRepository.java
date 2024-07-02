package com.trendex.trendex.domain.symbol.coinonesymbol.repository;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoinoneSymbolJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoinoneSymbolJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<CoinoneSymbol> coinoneSymbols) {

        String sql = "INSERT INTO coinone_symbol (symbol) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CoinoneSymbol coinoneSymbol = coinoneSymbols.get(i);
                ps.setString(1, coinoneSymbol.getSymbol());
            }

            @Override
            public int getBatchSize() {
                return coinoneSymbols.size();
            }
        });
    }

}
