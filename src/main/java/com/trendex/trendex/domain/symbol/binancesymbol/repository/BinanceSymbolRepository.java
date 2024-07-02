package com.trendex.trendex.domain.symbol.binancesymbol.repository;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinanceSymbolRepository extends JpaRepository<BinanceSymbol, Long> {
}
