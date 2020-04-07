package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.dto.CommentBookDto;

import java.util.List;

public interface LibraryService {
    BookDto comment(CommentBookDto request);

    BookDto returnBook(long bookId);

    BookDto reserveBook(long bookId);

    List<BookDto> findAll();
}
