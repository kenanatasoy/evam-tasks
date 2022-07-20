package com.example.invoiceservice.services;

import com.example.invoiceservice.entities.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();
    Client getClientById(Long id);
    Client createNewClient(Client client);
    Client updateClient(Long id, String firstName, String lastName);
    Client deleteClientById(Long id);
    Boolean existsById(Long id);

}
