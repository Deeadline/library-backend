package com.yoko.libraryproject.repository;

import com.yoko.libraryproject.entity.Book;
import com.yoko.libraryproject.entity.User;
import com.yoko.libraryproject.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findAllByUserAndLoanedByUser(User user, boolean isLoaned);

    Optional<UserBook> findByUserAndBook(User user, Book book);
}
