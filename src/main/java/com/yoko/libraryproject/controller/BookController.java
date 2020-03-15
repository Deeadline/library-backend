package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.repository.BookRepository;
import com.yoko.libraryproject.specification.BookCategoryFilterSpecification;
import com.yoko.libraryproject.specification.BookDateFilterSpecification;
import com.yoko.libraryproject.specification.BookNameFilterSpecification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Book> findAll(
            BookDateFilterSpecification dateSpecification,
            BookNameFilterSpecification nameSpecification,
            BookCategoryFilterSpecification bookSpecification) {
        return bookRepository.findAll(bookSpecification.or(nameSpecification.or(dateSpecification)));
    }


    @GetMapping({"/{id}"})
    public Long getById(@PathVariable Long id) {
        return id;
    }

    @PostMapping
    public String create(@RequestBody Book book) {
        return "Create";
    }

    @PutMapping({"/{id}"})
    public String update(@PathVariable Long id, @RequestBody Book book) {
        return "Update";
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable Long id) {
        return "Delete";
    }
}
