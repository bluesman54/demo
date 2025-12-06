package com.example.demo.service.impl;


import com.example.demo.entity.Book;
import com.example.demo.entity.Client;
import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.ClientService;
import com.example.demo.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ClientService clientService;
    private final BookService bookService;

    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    private Sale buildSale(Client client, Book book, Integer quantity) {
        BigDecimal totalPrice = book.getPrice().multiply(new BigDecimal(quantity));

        return Sale.builder()
                .client(client)
                .book(book)
                .salePrice(book.getPrice())
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }

    @Transactional
    public Sale createSale(Long clientId, Long bookId, Integer quantity) {
        Client client = clientService.findById(clientId);
        Book book = bookService.findById(bookId);

        if (book.getCount() < quantity) {
            throw new RuntimeException("Недоступно для продажи");
        }

        Sale sale = buildSale(client, book, quantity);
        bookService.sale(book.getId(), quantity);

        Sale savedSale = saleRepository.save(sale);
        log.info("Создана сделка {} клиентом {} на книгу {} кол-во {}",
                savedSale.getId(),
                client.getId(),
                book.getId(),
                quantity);

        return savedSale;
    }

    public void delete(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Сделка не найдена" + id));
        Book book = sale.getBook();

        bookService.recovery(book.getId(), sale.getQuantity());
        saleRepository.deleteById(id);
    }
}