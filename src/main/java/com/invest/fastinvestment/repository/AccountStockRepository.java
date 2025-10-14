package com.invest.fastinvestment.repository;

import com.invest.fastinvestment.entity.AccountStock;
import com.invest.fastinvestment.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
