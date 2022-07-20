package com.example.invoiceservice.controllers;

import com.example.invoiceservice.entities.Response;
import com.example.invoiceservice.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
@RequestScope
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAllPayments")
    public ResponseEntity<Response> getAllPayments(){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),
                "Returned all payments", paymentService.getAllPayments()));
    }

    @GetMapping("/getPaymentsByClientId/{clientId}")
    public ResponseEntity<Response> getPaymentsByClientId(@PathVariable Long clientId){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),
                "Returned payments of the given client", paymentService.getPaymentByClientId(clientId)));
    }

    @GetMapping("/getPaymentById/{id}")
    public ResponseEntity<Response> getPaymentById(@PathVariable Long id){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),
                "Returned the payment", paymentService.getPaymentById(id)));
    }


    @PostMapping("/makePaymentForClientUnpaidInvoices/{clientId}")
    public ResponseEntity<Response> makePaymentForClientUnpaidInvoices(@PathVariable Long clientId, @RequestParam BigDecimal givenAmount){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),
                "Made and returned the payment", paymentService.makePaymentForClientUnpaidInvoices(clientId, givenAmount)));
    }



}
