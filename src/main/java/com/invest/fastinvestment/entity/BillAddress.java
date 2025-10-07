package com.invest.fastinvestment.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tb_billaddress")
public class BillAddress {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @Column(name="city")
    private String city;

    @Column(name="street")
    private String street;

    @Column(name="number")
    private Integer number;
    @Column(name="postalcode")
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;

    public BillAddress() {
    }

    public BillAddress(UUID id, String city, String street, Integer number, String postalCode) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
