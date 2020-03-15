package com.yoko.libraryproject.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Spec(path = "releaseDate", params = {"releasedAfter", "releasedBefore"}, spec = Between.class)
public interface BookDateFilterSpecification extends NotDeletedBookSpecification {
}
