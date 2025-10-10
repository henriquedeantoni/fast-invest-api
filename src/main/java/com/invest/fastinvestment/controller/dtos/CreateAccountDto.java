package com.invest.fastinvestment.controller.dtos;

public record CreateAccountDto(String description, String accountNumber, String city, String street, Integer number, String postalCode) {
}
