package com.example.demo.service;

import com.example.demo.entity.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> findAllSales();
    Sale createSale(Long clientId, Long bookId);
    void delete(Long id);
}
