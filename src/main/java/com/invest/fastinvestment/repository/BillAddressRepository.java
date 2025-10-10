package com.invest.fastinvestment.repository;

import com.invest.fastinvestment.entity.BillAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillAddressRepository extends JpaRepository<BillAddress, UUID> {
}

