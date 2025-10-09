package com.invest.fastinvestment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStockRepository, UUID> {
}
