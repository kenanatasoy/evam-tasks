package com.example.invoiceservice.controllers;

import com.example.invoiceservice.entities.Invoice;
import com.example.invoiceservice.entities.Response;
import com.example.invoiceservice.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;

@RestController
@RequestMapping("/invoices")
@RequestScope
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAllInvoices")
    public ResponseEntity<Response> getAllInvoices(){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Returned all invoices",
                invoiceService.getAllInvoices()));
    }


    @GetMapping("/getUnPaidInvoicesByClientId/{clientId}")
    public ResponseEntity<Response> getUnPaidInvoicesByClientId(@PathVariable Long clientId){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Returned unpaid invoices of the given client",
                invoiceService.getUnPaidInvoicesByClientId(clientId)));
    }

    @GetMapping("/getUnPaidInvoicesTotalDebtByClientId/{clientId}")
    public ResponseEntity<Response> getUnPaidInvoicesTotalDebtByClientId(@PathVariable Long clientId){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Returned unpaid invoices' total debt of the given client",
                invoiceService.getUnPaidInvoicesTotalDebtByClientId(clientId)));
    }

    @GetMapping("/getInvoice/{id}")
    public ResponseEntity<Response> getInvoiceById(@PathVariable Long id){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Returned the invoice",
                invoiceService.getInvoiceById(id)));
    }

    @PostMapping("/createNewInvoice")
    public ResponseEntity<Response> createNewInvoice(@RequestBody @Validated Invoice invoice){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Created and returned the invoice",
                invoiceService.createNewInvoice(invoice)));
    }


    @PutMapping("/updateInvoice/{id}")
    public ResponseEntity<Response> updateInvoice(@PathVariable Long id,
                                                 @RequestParam BigDecimal subTotal){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Updated and returned the updated invoice",
                invoiceService.updateInvoice(id, subTotal)));
    }


    @DeleteMapping("/deleteInvoiceById/{id}")
    public ResponseEntity<Response> deleteInvoiceById(@PathVariable Long id){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Deleted and returned the deleted invoice",
                invoiceService.deleteInvoiceById(id)));
    }



}
