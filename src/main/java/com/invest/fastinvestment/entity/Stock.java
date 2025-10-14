package com.invest.fastinvestment.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="tb_stocks")
public class Stock {

    @Id
    @Column(name="stock_id")
    private String stockId;

    @Column(name="description")
    private String description;

    @Column(name="category")
    private String category;

    public Stock() {}

    public Stock(String stockId, String description, String category) {
        this.stockId = stockId;
        this.description = description;
        this.category = category;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
