package com.yoko.libraryproject.specification;

import com.yoko.libraryproject.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "releaseDate", params = {"releasedAfter", "releasedBefore"}, spec = Between.class)
public interface BookDateFilterSpecification extends Specification<Book> {
}
