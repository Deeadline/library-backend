package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.CategoryDto;
import com.yoko.libraryproject.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR')")
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ResponseBody
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }
}
