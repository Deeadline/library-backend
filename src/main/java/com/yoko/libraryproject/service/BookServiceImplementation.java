package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.exception.BookIsbnAlreadyExistException;
import com.yoko.libraryproject.exception.BookNotFoundException;
import com.yoko.libraryproject.repository.BookRepository;
import com.yoko.libraryproject.repository.CategoryRepository;
import com.yoko.libraryproject.repository.UserBookRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements BookService {

    final CategoryRepository categoryRepository;
    final BookRepository bookRepository;
    final UserBookRepository userBookRepository;
    final Mapper mapper;

    public BookServiceImplementation(
            CategoryRepository categoryRepository, BookRepository bookRepository,
            UserBookRepository userBookRepository,
            Mapper mapper
    ) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
        this.mapper = mapper;
    }

    public Iterable<BookDto> findAll(Specification<Book> specification) {
        Collection<Book> books = bookRepository.findAll(specification);
        return books.stream().map(mapper::convertToBookDto).collect(Collectors.toList());
    }

    public BookDto findById(Long id) {
        return bookRepository.findById(id).map(mapper::convertToBookDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDto create(@NotNull BookDto book) {
        bookRepository.findByIsbn(book.getIsbn()).orElseThrow(BookIsbnAlreadyExistException::new);
        return mapper.convertToBookDto(bookRepository.save(mapper.convertToBook(book)));
    }

    public BookDto update(Long id, BookDto bookDto) {
        return bookRepository.findById(id).map(b -> {
            b.setAuthor(bookDto.getAuthor());
            b.setIsbn(bookDto.getIsbn());
            b.setPublishingHouse(bookDto.getPublishingHouse());
            b.setTitle(bookDto.getTitle());
            b.setReleaseDate(bookDto.getReleaseDate());
            return mapper.convertToBookDto(bookRepository.save(b));
        }).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
