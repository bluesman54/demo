package com.example.demo.service;

import com.example.demo.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    Client update(Long id, Client client);
    void delete(Long id);
}
