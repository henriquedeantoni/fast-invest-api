package com.invest.fastinvestment.controller;

import java.io.Serializable;

public record UpdateInvestorDto(String username, String password, String email, String name) implements Serializable {
}
