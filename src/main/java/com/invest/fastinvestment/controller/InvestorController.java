package com.invest.fastinvestment.controller;

import com.invest.fastinvestment.entity.Investor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/investors")
public class InvestorController {

    @PostMapping
    public ResponseEntity<Investor> createInvestor(@RequestBody String body){
        return null;
    }

    @GetMapping("/{investorId}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable("investorId") String id){
        return null;
    }
}
