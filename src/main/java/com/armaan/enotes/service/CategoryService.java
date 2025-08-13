package com.armaan.enotes.service;

import java.util.List;

import com.armaan.enotes.dto.CategoryDto;
import com.armaan.enotes.dto.CategoryResponse;
import com.armaan.enotes.entity.Category;

public interface CategoryService {

    // Create
    CategoryResponse createCategory(CategoryDto categoryDto);

    // Read (active only)
    List<Category> getAllCategories();

    CategoryResponse getCategoryById(Integer id);

    // Update (active only)
    CategoryResponse updateCategory(Integer id, CategoryDto categoryDto);

    // Soft delete (mark as deleted)
    CategoryResponse deleteCategory(Integer id);
}
