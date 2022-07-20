package com.example.invoiceservice.services.business;

import com.example.invoiceservice.entities.Client;
import com.example.invoiceservice.entities.Payment;
import com.example.invoiceservice.exceptions.InsufficientAmountException;
import com.example.invoiceservice.exceptions.NoNeedForPaymentException;
import com.example.invoiceservice.repositories.PaymentRepository;
import com.example.invoiceservice.services.ClientService;
import com.example.invoiceservice.services.InvoiceService;
import com.example.invoiceservice.services.PaymentService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;
    private final ClientService clientService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, InvoiceService invoiceService, ClientService clientService) {
        this.paymentRepository = paymentRepository;
        this.invoiceService = invoiceService;
        this.clientService = clientService;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentByClientId(Long clientId) {
        return paymentRepository.findPaymentByClient_Id(clientId).orElseThrow(() ->
                new EntityNotFoundException("Could not find the payment of the given client"));
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Could not find the payment"));
    }


    @Override
    public Payment makePaymentForClientUnpaidInvoices(Long clientId, BigDecimal givenAmount) {

        BigDecimal totalDebt = invoiceService.getUnPaidInvoicesTotalDebtByClientId(clientId);

        if(totalDebt.compareTo(new BigDecimal("0")) == 0)
            throw new NoNeedForPaymentException("Your total debt is 0. You do not need to make any payments for now!");

        if(givenAmount.compareTo(totalDebt) < 0)
            throw new InsufficientAmountException("The deposited amount is insufficient, please deposit "
                    + (totalDebt.subtract(givenAmount)) + " dollars more");

        Client client = clientService.getClientById(clientId);

        Payment payment = Payment.builder()
                .paidTotal(totalDebt)
                .extra(givenAmount.subtract(totalDebt))
                .client(client)
                .build();

        payment = paymentRepository.save(payment);
        invoiceService.getUnPaidInvoicesByClientId(clientId)
                .forEach((invoice) -> invoice.setIsPaid(true));

        return payment;

    }



}
