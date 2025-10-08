package com.invest.fastinvestment.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="tb_accounts_stocks")
public class AccountStock {

    @EmbeddedId
    private AccountStockId accountStockId;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="purchasePrice")
    private BigDecimal purchasePrice;

    public AccountStock() {
    }

    public AccountStock(AccountStockId accountStockId, Account account, Stock stock, Integer quantity, BigDecimal purchasePrice) {
        this.accountStockId = accountStockId;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public AccountStockId getAccountStockId() {
        return accountStockId;
    }

    public void setAccountStockId(AccountStockId accountStockId) {
        this.accountStockId = accountStockId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return purchasePrice;
    }

    public void setPrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
