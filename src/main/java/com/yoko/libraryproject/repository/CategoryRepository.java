package com.yoko.libraryproject.repository;

import com.yoko.libraryproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
