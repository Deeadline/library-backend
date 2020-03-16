package com.yoko.libraryproject.service;

import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.exception.BookNotFoundException;
import com.yoko.libraryproject.repository.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    protected final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Iterable<Book> findAll(Specification<Book> specification) {
        return bookRepository.findAll(specification);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        return bookRepository.findById(id).map(b -> {
            b.setAuthor(book.getAuthor());
            b.setCategories(book.getCategories());
            b.setIsbn(book.getIsbn());
            b.setPublishingHouse(book.getPublishingHouse());
            b.setTitle(book.getTitle());
            b.setReleaseDate(book.getReleaseDate());
            return bookRepository.save(b);
        }).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
