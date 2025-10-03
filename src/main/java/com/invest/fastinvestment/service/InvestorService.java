package com.invest.fastinvestment.service;

import com.invest.fastinvestment.controller.CreateInvestorDto;
import com.invest.fastinvestment.entity.Investor;
import com.invest.fastinvestment.repository.InvestorRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class InvestorService {

    private InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public UUID createInvestor(CreateInvestorDto dto) {

        var entity =  new Investor(
                null,
                dto.username(),
                dto.email(),
                dto.password(),
                dto.name(),
                Instant.now(),
                null);

        var investorSaved = investorRepository.save(entity);

        return investorSaved.getInvestorId();
    }
}
