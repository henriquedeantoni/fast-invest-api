package com.invest.fastinvestment.repository;

import com.invest.fastinvestment.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, UUID> {
}
