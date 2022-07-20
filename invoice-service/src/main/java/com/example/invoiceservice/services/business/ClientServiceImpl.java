package com.example.invoiceservice.services.business;

import com.example.invoiceservice.entities.Client;
import com.example.invoiceservice.repositories.ClientRepository;
import com.example.invoiceservice.services.ClientService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client cannot be found!"));
    }

    @Override
    public Client createNewClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, String firstName, String lastName) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client to be updated cannot be found!"));
        if(Objects.nonNull(firstName) && !firstName.isBlank() && !client.getFirstName().equals(firstName))
            client.setFirstName(firstName);
        if(Objects.nonNull(lastName) && !lastName.isBlank() && !client.getLastName().equals(lastName))
            client.setLastName(lastName);
        return client;

    }


    @Override
    public Client deleteClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Client to be deleted cannot be found!"));
        clientRepository.deleteById(id);
        return client;
    }

    @Override
    public Boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }

}
