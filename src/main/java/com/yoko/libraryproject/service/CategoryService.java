package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();
}
