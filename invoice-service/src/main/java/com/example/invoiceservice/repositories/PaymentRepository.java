package com.example.invoiceservice.repositories;

import com.example.invoiceservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentByClient_Id(Long clientId);

}
