package com.example.invoiceservice.services.business;

import com.example.invoiceservice.entities.Invoice;
import com.example.invoiceservice.exceptions.InvalidFundsException;
import com.example.invoiceservice.repositories.InvoiceRepository;
import com.example.invoiceservice.services.ClientService;
import com.example.invoiceservice.services.InvoiceService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientService clientService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ClientService clientService) {
        this.invoiceRepository = invoiceRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getUnPaidInvoicesByClientId(Long clientId) {
        if(!clientService.existsById(clientId))
            throw new EntityNotFoundException("Client could not be found");
        return invoiceRepository.findAllByIsPaidFalseAndClient_Id(clientId);
    }

    @Override
    public BigDecimal getUnPaidInvoicesTotalDebtByClientId(Long clientId) {
        if(!clientService.existsById(clientId))
            throw new EntityNotFoundException("Client could not be found");
        return invoiceRepository.findAllByIsPaidFalseAndClient_Id(clientId).stream()
                .map(Invoice::getDebt)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal("0"));
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Invoice could not be found!"));
    }

    @Override
    public Invoice createNewInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice updateInvoice(Long id, BigDecimal debt) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Invoice to be updated could not be found!"));
        if(Objects.isNull(debt) || debt.compareTo(new BigDecimal("0")) < 0)
            throw new InvalidFundsException("Invoice debt cannot be set to an invalid amount!");
        invoice.setDebt(debt);
        return invoice;
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Invoice to be deleted could not be found!"));
        invoiceRepository.deleteById(id);
        return invoice;
    }

}
