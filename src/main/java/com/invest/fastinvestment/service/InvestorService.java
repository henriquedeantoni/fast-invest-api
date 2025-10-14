package com.invest.fastinvestment.service;

import com.invest.fastinvestment.controller.dtos.CreateAccountDto;
import com.invest.fastinvestment.controller.dtos.CreateInvestorDto;
import com.invest.fastinvestment.controller.dtos.UpdateInvestorDto;
import com.invest.fastinvestment.entity.Account;
import com.invest.fastinvestment.entity.BillAddress;
import com.invest.fastinvestment.entity.Investor;
import com.invest.fastinvestment.exceptions.InvestorCreationException;
import com.invest.fastinvestment.repository.AccountRepository;
import com.invest.fastinvestment.repository.BillAddressRepository;
import com.invest.fastinvestment.repository.InvestorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class    InvestorService {

    private InvestorRepository investorRepository;
    private AccountRepository accountRepository;
    private BillAddressRepository billAddressRepository;

    public InvestorService(InvestorRepository investorRepository,
                           AccountRepository accountRepository,
                           BillAddressRepository billAddressRepository) {
        this.investorRepository = investorRepository;
        this.accountRepository = accountRepository;
        this.billAddressRepository = billAddressRepository;
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

    public void createAccount(String investorId, CreateAccountDto createAccountDto) {

        var investor = investorRepository.findById(UUID.fromString(investorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor Not Found"));

        Account account = new Account(
                UUID.randomUUID(),
                createAccountDto.accountNumber(),
                createAccountDto.description(),
                investor,
                null,
                new ArrayList<>()
        );

        var newAccountCreated = accountRepository.save(account);

        BillAddress billAddress = new BillAddress(
            newAccountCreated.getAccountiD(),
                account,
                createAccountDto.city(),
                createAccountDto.street(),
                createAccountDto.number(),
                createAccountDto.postalCode()
        );

        billAddressRepository.save(billAddress);
    }

    public Object listAccounts(String investorId) {
        
    }
}
