package com.trendex.trendex.domain.symbol.binancesymbol.repository;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BinanceSymbolJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BinanceSymbolJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<BinanceSymbol> binanceSymbols) {

        String sql = "INSERT INTO binance_symbol (symbol) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BinanceSymbol binanceSymbol = binanceSymbols.get(i);
                ps.setString(1, binanceSymbol.getSymbol());
            }

            @Override
            public int getBatchSize() {
                return binanceSymbols.size();
            }
        });
    }

}
