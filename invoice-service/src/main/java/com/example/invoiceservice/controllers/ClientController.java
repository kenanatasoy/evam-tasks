package com.example.invoiceservice.controllers;

import com.example.invoiceservice.entities.Client;
import com.example.invoiceservice.entities.Response;
import com.example.invoiceservice.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/clients")
@RequestScope
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<Response> getAllClients(){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Returned all clients",
                clientService.getAllClients()));
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Response> getClientById(@PathVariable Long id){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Returned the client",
                clientService.getClientById(id)));
    }

    @PostMapping("/createNewClient")
    public ResponseEntity<Response> createNewClient(@RequestBody @Validated Client client){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Created and returned the client",
                clientService.createNewClient(client)));
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Response> updateClient(@PathVariable Long id,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Updated and returned the updated client",
                clientService.updateClient(id, firstName, lastName)));
    }

    @DeleteMapping("/deleteClientById/{id}")
    public ResponseEntity<Response> deleteClientById(@PathVariable Long id){
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"Deleted and returned the deleted client",
                clientService.deleteClientById(id)));
    }


}
