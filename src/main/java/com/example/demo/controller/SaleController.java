package com.example.demo.controller;

import com.example.demo.service.BookService;
import com.example.demo.service.ClientService;
import com.example.demo.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    private final BookService bookService;
    private final ClientService clientService;

    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.findAllSales());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("books", bookService.findAll());
        return "sales";
    }

    @PostMapping
    public String addSale(@RequestParam("clients") Long clientId,
                          @RequestParam("book") Long bookId) {
        saleService.createSale(clientId, bookId);
        return "redirect:/sales";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return "redirect:/sales";
    }
}
