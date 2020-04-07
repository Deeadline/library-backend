package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.entity.Category;
import com.yoko.libraryproject.entity.User;
import com.yoko.libraryproject.repository.CategoryRepository;
import com.yoko.libraryproject.repository.UserBookRepository;
import com.yoko.libraryproject.repository.UserRepository;
import com.yoko.libraryproject.security.service.UserDetailsImplementation;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class Mapper {
    final ModelMapper modelMapper;
    final UserBookRepository userBookRepository;
    final CategoryRepository categoryRepository;
    final UserRepository userRepository;

    public Mapper(ModelMapper modelMapper, UserBookRepository userBookRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userBookRepository = userBookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @NotNull
    public BookDto convertToBookDto(@NotNull Book book) {
        var bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setCategoryIds(book.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
        var userBooks = userBookRepository
                .findAllByBook(book)
                .stream()
                .map(b -> new CommentBookDto(b.getId(), b.getComment(), b.getUser().getUsername()))
                .collect(Collectors.toList());
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        userBookRepository.findByUserAndBook(user, book)
                .ifPresent(userBook -> bookDto.setIsLoaned(userBook.isLoanedByUser()));
        bookDto.setComments(userBooks);
        return bookDto;
    }

    public Book convertToBook(@NotNull BookDto bookDto) {
        var book = modelMapper.map(bookDto, Book.class);
        var categories = new HashSet<>(categoryRepository.findAllById(bookDto.getCategoryIds()));
        book.setCategories(categories);
        return book;
    }
}
