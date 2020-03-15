package com.yoko.libraryproject.specification;

import com.yoko.libraryproject.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "deleted", constVal = "false", spec = Equal.class)
public interface NotDeletedBookSpecification extends Specification<Book> {
}
