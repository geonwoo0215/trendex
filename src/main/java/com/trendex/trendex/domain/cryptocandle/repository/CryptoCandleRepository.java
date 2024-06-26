package com.trendex.trendex.domain.cryptocandle.repository;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCandleRepository extends JpaRepository<CryptoCandle, Long> {
}
