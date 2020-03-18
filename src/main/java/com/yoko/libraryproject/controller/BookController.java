package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.service.BookService;
import com.yoko.libraryproject.specification.BookCategoryFilterSpecification;
import com.yoko.libraryproject.specification.BookDateFilterSpecification;
import com.yoko.libraryproject.specification.BookNameFilterSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json", consumes = "application/json")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<BookDto> findAll(
            BookDateFilterSpecification dateSpecification,
            BookNameFilterSpecification nameSpecification,
            BookCategoryFilterSpecification bookSpecification) {
        return bookService.findAll(bookSpecification.or(nameSpecification.or(dateSpecification)));
    }


    @GetMapping({"/{id}"})
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto update(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.update(id, book);
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus delete(@PathVariable Long id) {
        bookService.delete(id);
        return HttpStatus.NO_CONTENT;
    }
}
