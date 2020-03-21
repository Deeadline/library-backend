package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.CategoryDto;
import com.yoko.libraryproject.entity.Category;
import com.yoko.libraryproject.repository.CategoryRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {

    final ModelMapper modelMapper;
    final CategoryRepository categoryRepository;

    CategoryServiceImplementation(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToCategoryDto).collect(Collectors.toList());
    }

    private CategoryDto convertToCategoryDto(@NotNull Category book) {
        return modelMapper.map(book, CategoryDto.class);
    }
}
