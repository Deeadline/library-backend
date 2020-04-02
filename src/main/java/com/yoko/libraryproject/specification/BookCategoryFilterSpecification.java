package com.yoko.libraryproject.specification;

import com.yoko.libraryproject.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "categories", alias = "cat")
@Spec(path = "cat.name", params = "category", spec = In.class)
public interface BookCategoryFilterSpecification extends Specification<Book> {
}
