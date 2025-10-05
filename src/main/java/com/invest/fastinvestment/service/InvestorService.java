package com.invest.fastinvestment.service;

import com.invest.fastinvestment.controller.CreateInvestorDto;
import com.invest.fastinvestment.controller.UpdateInvestorDto;
import com.invest.fastinvestment.entity.Investor;
import com.invest.fastinvestment.exceptions.InvestorCreationException;
import com.invest.fastinvestment.repository.InvestorRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class    InvestorService {

    private InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public UUID createInvestor(CreateInvestorDto dto) {

        try{
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
        } catch(Exception e) {
            throw new InvestorCreationException("Failed to create investor user", e);
        }
    }

    public Optional<Investor> getInvestorById(String investorId) {
        return investorRepository.findById(UUID.fromString(investorId));
    }

    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }

    public void updateInvestorById(String investorId, UpdateInvestorDto updateInvestorDto) {
        var investorEntity = investorRepository.findById(UUID.fromString(investorId));

        if (investorEntity.isPresent()) {
            var investor = investorEntity.get();
            if(updateInvestorDto.username() != null){
                investor.setUsername(updateInvestorDto.username());
            }
            if(updateInvestorDto.email() != null){
                investor.setEmail(updateInvestorDto.email());
            }
            if(updateInvestorDto.password() != null){
                investor.setPassword(updateInvestorDto.password());
            }
            if(updateInvestorDto.name() != null){
                investor.setName(updateInvestorDto.name());
            }
            investorRepository.save(investor);
        }
    }

    public void deleteInvestorById(String investorId) {
        var id = UUID.fromString(investorId);
        var investorExists = investorRepository.existsById(id);
        if (investorExists) {
            investorRepository.deleteById(id);
        }
    }

}
