package com.example.invoiceservice.services;

import com.example.invoiceservice.entities.Invoice;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {

    List<Invoice> getAllInvoices();
    List<Invoice> getUnPaidInvoicesByClientId(Long clientId);
    BigDecimal getUnPaidInvoicesTotalDebtByClientId(Long clientId);
    Invoice getInvoiceById(Long id);
    Invoice createNewInvoice(Invoice invoice);
    Invoice updateInvoice(Long id, BigDecimal debt);
    Invoice deleteInvoiceById(Long id);

}
