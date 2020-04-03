package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.AvailableResponse;
import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.entity.User;
import com.yoko.libraryproject.entity.UserBook;
import com.yoko.libraryproject.exception.BookNotFoundException;
import com.yoko.libraryproject.exception.UserBookNotFoundException;
import com.yoko.libraryproject.exception.UserBookNotLoanedException;
import com.yoko.libraryproject.repository.BookRepository;
import com.yoko.libraryproject.repository.UserBookRepository;
import com.yoko.libraryproject.repository.UserRepository;
import com.yoko.libraryproject.security.service.UserDetailsImplementation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LibraryServiceImplementation implements LibraryService {

    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final Mapper mapper;

    public LibraryServiceImplementation(
            UserRepository userRepository,
            UserBookRepository userBookRepository,
            BookRepository bookRepository,
            Mapper mapper) {
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public BookDto comment(CommentBookDto request) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new BookNotFoundException(request.getBookId()));
        UserBook userBook = userBookRepository.findByUserAndBook(user, book).orElse(new UserBook());
        if (userBook.getId() == 0) {
            userBook.setBook(book);
            userBook.setUser(user);
            userBook.setLoanedByUser(false);
        }
        userBook.setComment(request.getComment());
        userBookRepository.save(userBook);
        return mapper.convertToBookDto(bookRepository.save(book));
    }

    public BookDto returnBook(long bookId) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        UserBook userBook = userBookRepository.findByUserAndBook(user, book).orElseThrow(() -> new UserBookNotFoundException(user.getId(), book.getId()));
        if (userBook.isLoanedByUser()) {
            book.setLoaned(false);
            return mapper.convertToBookDto(bookRepository.save(book));
        }
        throw new UserBookNotLoanedException(book.getId());
    }

    public BookDto reserveBook(long bookId) {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        book.setLoaned(true);
        UserBook userBook = userBookRepository.findByUserAndBook(user, book).orElse(new UserBook());
        if (userBook.getId() == 0) {
            userBook.setBook(book);
            userBook.setUser(user);
        }
        userBook.setLoanedByUser(true);
        userBookRepository.save(userBook);
        return mapper.convertToBookDto(bookRepository.save(book));
    }

    public List<BookDto> findAll() {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        List<UserBook> userBooks = userBookRepository.findAllByUserAndLoanedByUser(user, true);
        Stream<Book> books = userBooks.stream().map(UserBook::getBook);
        return books.map(mapper::convertToBookDto).collect(Collectors.toList());
    }

    public AvailableResponse checkAvailability(long bookId) {
        return bookRepository.findById(bookId).map(b -> new AvailableResponse(b.isLoaned())).orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
