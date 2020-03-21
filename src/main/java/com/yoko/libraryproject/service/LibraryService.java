package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.AvailableResponse;
import com.yoko.libraryproject.dto.BookDto;
import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.dto.ReserveDto;

import java.util.List;

public interface LibraryService {
    boolean comment(long bookId, CommentBookDto request);

    boolean returnBook(long bookId);

    String reserveBook(ReserveDto reserveRequest);

    List<BookDto> findAll();

    AvailableResponse checkAvailability(long bookId);
}
