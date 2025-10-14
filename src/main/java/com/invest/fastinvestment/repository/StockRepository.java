package com.invest.fastinvestment.repository;

import com.invest.fastinvestment.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, String> {
}
