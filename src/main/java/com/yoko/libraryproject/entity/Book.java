package com.yoko.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false, unique = true, length = 13)
    @NonNull
    private String isbn;

    @Column(nullable = false)
    @NonNull
    private String author;

    @Column(nullable = false)
    @NonNull
    private String publishingHouse;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private Date releaseDate;

    private boolean deleted;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
}
