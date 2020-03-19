package com.yoko.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE book SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
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

    @Column(nullable = false, length = 4)
    @NonNull
    private String releaseDate;

    private boolean isLoaned;
    private boolean deleted;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("books")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<UserBook> userBooks = new HashSet<>();
}
