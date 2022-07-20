package com.example.invoiceservice.services;

import com.example.invoiceservice.entities.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();
    Payment getPaymentByClientId(Long clientId);
    Payment getPaymentById(Long id);
    Payment makePaymentForClientUnpaidInvoices(Long clientId, BigDecimal givenAmount);


}
