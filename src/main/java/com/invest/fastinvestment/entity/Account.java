package com.invest.fastinvestment.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tb_account")
public class Account {

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountiD;

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="investor_id")
    private Investor investor;

    @OneToOne(mappedBy="account")
    @PrimaryKeyJoinColumn
    private BillAddress billAddress;

    public Account() {
    }

    public Account(UUID accountiD, String accountNumber, String description) {
        this.accountiD = accountiD;
        this.accountNumber = accountNumber;
        this.description = description;
    }

    public UUID getAccountiD() {
        return accountiD;
    }

    public void setAccountiD(UUID accountiD) {
        this.accountiD = accountiD;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
}
