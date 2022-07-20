package com.example.invoiceservice.exceptions;

public class NoNeedForPaymentException extends RuntimeException {

    public NoNeedForPaymentException() {
        super();
    }

    public NoNeedForPaymentException(String message) {
        super(message);
    }

    public NoNeedForPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNeedForPaymentException(Throwable cause) {
        super(cause);
    }
}
