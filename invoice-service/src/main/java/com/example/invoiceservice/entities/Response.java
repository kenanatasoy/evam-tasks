package com.example.invoiceservice.entities;

public record Response(Integer status, String message, Object returned){
}
