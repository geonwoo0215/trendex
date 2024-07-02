package com.trendex.trendex.domain.symbol.bithumbsymbol.repository;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BithumbSymbolRepository extends JpaRepository<BithumbSymbol, Long> {
}
