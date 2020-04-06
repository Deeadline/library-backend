package com.yoko.libraryproject.specification;

import com.yoko.libraryproject.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "releaseDate", params = "year", spec = In.class)
public interface BookDateFilterSpecification extends Specification<Book> {
}
