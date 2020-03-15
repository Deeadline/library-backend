package com.yoko.libraryproject.specification;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Or({
        @Spec(path = "title", spec = LikeIgnoreCase.class),
        @Spec(path = "author", spec = LikeIgnoreCase.class)
})
public interface BookNameFilterSpecification extends NotDeletedBookSpecification {
}
