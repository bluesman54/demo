package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book) {
        Book existingBook = findById(id);
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setCount(book.getCount());

        return bookRepository.save(existingBook);
    }

    public void sale(Long id, Integer quantity) {
        Book book = findById(id);
        book.setCount(book.getCount() - quantity);
    }

    public void recovery(Long id, Integer quantity) {
        Book book = findById(id);
        book.setCount(book.getCount() + quantity);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
