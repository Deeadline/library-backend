package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.exception.BookNotFoundException;
import com.yoko.libraryproject.repository.BookRepository;
import com.yoko.libraryproject.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookService {

    final BookRepository bookRepository;
    final CategoryRepository categoryRepository;

    final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    public Iterable<BookDto> findAll(Specification<Book> specification) {
        Collection<Book> books = bookRepository.findAll(specification);
        return books.stream().map(this::convertToBookDto).collect(Collectors.toList());
    }

    public BookDto findById(Long id) {
        return bookRepository.findById(id).map(this::convertToBookDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDto create(Book book) {
        return convertToBookDto(bookRepository.save(book));
    }

    public BookDto update(Long id, BookDto bookDto) {
        return bookRepository.findById(id).map(b -> {
            b.setAuthor(bookDto.getAuthor());
            b.setIsbn(bookDto.getIsbn());
            b.setPublishingHouse(bookDto.getPublishingHouse());
            b.setTitle(bookDto.getTitle());
            b.setReleaseDate(bookDto.getReleaseDate());
            return this.convertToBookDto(bookRepository.save(b));
        }).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDto convertToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
}
