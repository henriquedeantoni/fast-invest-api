package com.invest.fastinvestment.controller;

import com.invest.fastinvestment.entity.Investor;
import com.invest.fastinvestment.repository.InvestorRepository;
import com.invest.fastinvestment.service.InvestorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/investors")
public class InvestorController {

    private InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @PostMapping
    public ResponseEntity<Investor> createInvestor(@RequestBody CreateInvestorDto createInvestorDto){
        var id = investorService.createInvestor(createInvestorDto);

        return ResponseEntity.created(URI.create("/v1/investors/" + id.toString())).build();
    }

    @GetMapping("/{investorId}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable("investorId") String id){
        var investor = investorService.getInvestorById(id);
        return investor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Investor>> getAllInvestors(){
        var list = investorService.getAllInvestors();
        return ResponseEntity.ok().body(list);
    }
}
