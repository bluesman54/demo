package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    Book update(Long id, Book book);
    void sale(Long id);
    void recovery(Long id);
    void delete(Long id);
}
