package com.invest.fastinvestment.repository;

import com.invest.fastinvestment.entity.Account;
import com.invest.fastinvestment.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountStockId> {
}
