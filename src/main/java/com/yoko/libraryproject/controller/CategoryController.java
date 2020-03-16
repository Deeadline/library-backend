package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.entity.Category;
import com.yoko.libraryproject.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories", produces = "application/json")
public class CategoryController {

    final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
