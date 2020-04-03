package com.yoko.libraryproject.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BookDto {
    private long id;
    private String title;
    private String isbn;
    private String author;
    private String publishingHouse;
    private String releaseDate;
    private Set<Long> categoryIds = new HashSet<>();
    private List<CommentBookDto> comments = new ArrayList<>();
}
