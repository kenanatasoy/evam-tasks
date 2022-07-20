package com.example.invoiceservice.repositories;

import com.example.invoiceservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
