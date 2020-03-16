package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.service.BookService;
import com.yoko.libraryproject.specification.BookCategoryFilterSpecification;
import com.yoko.libraryproject.specification.BookDateFilterSpecification;
import com.yoko.libraryproject.specification.BookNameFilterSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Book> findAll(
            BookDateFilterSpecification dateSpecification,
            BookNameFilterSpecification nameSpecification,
            BookCategoryFilterSpecification bookSpecification) {
        return bookService.findAll(bookSpecification.or(nameSpecification.or(dateSpecification)));
    }


    @GetMapping({"/{id}"})
    public Book getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping({"/{id}"})
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping({"/{id}"})
    public HttpStatus delete(@PathVariable Long id) {
        bookService.delete(id);
        return HttpStatus.NO_CONTENT;
    }
}
