package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.AvailableResponse;
import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.dto.ReserveDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.entity.Category;
import com.yoko.libraryproject.entity.User;
import com.yoko.libraryproject.entity.UserBook;
import com.yoko.libraryproject.exception.BookNotFoundException;
import com.yoko.libraryproject.exception.UserBookNotFoundException;
import com.yoko.libraryproject.repository.BookRepository;
import com.yoko.libraryproject.repository.UserBookRepository;
import com.yoko.libraryproject.repository.UserRepository;
import com.yoko.libraryproject.security.service.UserDetailsImplementation;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImplementation implements LibraryService {

    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public LibraryServiceImplementation(
            UserRepository userRepository,
            UserBookRepository userBookRepository,
            BookRepository bookRepository,
            ModelMapper modelMapper
    ) {
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public boolean comment(long bookId, CommentBookDto request) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        UserBook userBook = userBookRepository.findByUserAndBook(user, book).orElse(new UserBook());
        if (userBook.getId() == 0) {
            userBook.setBook(book);
            userBook.setUser(user);
            userBook.setLoanedByUser(false);
        }
        userBook.setComment(request.getComment());
        userBookRepository.save(userBook);
        bookRepository.save(book);
        return true;
    }

    public boolean returnBook(long bookId) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        UserBook userBook = userBookRepository.findByUserAndBook(user, book).orElseThrow(() -> new UserBookNotFoundException(user.getId(), book.getId()));
        if (userBook.isLoanedByUser()) {
            book.setLoaned(false);
            bookRepository.save(book);
        }
        return true;
    }

    public String reserveBook(ReserveDto reserveRequest) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(reserveRequest.getBookId()).orElseThrow(() -> new BookNotFoundException(reserveRequest.getBookId()));
        book.setLoaned(true);
        UserBook userBook = new UserBook();
        userBook.setBook(book);
        userBook.setUser(user);
        userBook.setLoanedByUser(true);
        userBookRepository.save(userBook);
        bookRepository.save(book);
        return "ok";
    }

    public List<BookDto> findAll() {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        List<UserBook> userBooks = userBookRepository.findAllByUserAndLoanedByUser(user, true);
        return userBooks.stream().map(UserBook::getBook).map(this::convertToBookDto).collect(Collectors.toList());
    }

    public AvailableResponse checkAvailability(long bookId) {
        return bookRepository.findById(bookId).map(b -> new AvailableResponse(b.isLoaned())).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private BookDto convertToBookDto(@NotNull Book book) {
        var bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setCategoryIds(book.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
        return bookDto;
    }
}
