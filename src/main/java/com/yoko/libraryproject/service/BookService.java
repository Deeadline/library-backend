package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public interface BookService {
    Iterable<BookDto> findAll(Specification<Book> specification);

    BookDto findById(Long id);

    BookDto create(BookDto book);

    BookDto update(Long id, BookDto bookDto);

    void delete(Long id);
}
