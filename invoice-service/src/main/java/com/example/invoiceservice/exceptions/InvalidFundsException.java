package com.example.invoiceservice.exceptions;

public class InvalidFundsException extends RuntimeException {
    
    public InvalidFundsException() {
        super();
    }

    public InvalidFundsException(String message) {
        super(message);
    }

    public InvalidFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFundsException(Throwable cause) {
        super(cause);
    }
    
}
