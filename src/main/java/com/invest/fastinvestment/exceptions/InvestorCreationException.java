package com.invest.fastinvestment.exceptions;

public class InvestorCreationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvestorCreationException(String message) {
        super(message);
    }

    public InvestorCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
