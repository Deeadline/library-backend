package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.service.BookService;
import com.yoko.libraryproject.specification.BookCategoryFilterSpecification;
import com.yoko.libraryproject.specification.BookDateFilterSpecification;
import com.yoko.libraryproject.specification.BookNameFilterSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json", consumes = "application/json")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR')")
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
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public BookDto create(@RequestBody BookDto book) {
        return bookService.create(book);
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public BookDto update(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.update(id, book);
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
