package com.armaan.enotes.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.armaan.enotes.dto.CategoryDto;
import com.armaan.enotes.dto.CategoryResponse;
import com.armaan.enotes.entity.Category;
import com.armaan.enotes.exception.ResourceNotFoundException;
import com.armaan.enotes.mapper.CategoryMapper;
import com.armaan.enotes.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        
        Category category=categoryMapper.toEntity(categoryDto);

        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(1); // Hardcoded for now, later from logged-in user
        category.setIsActive(true);

        Category savedCategory=categoryRepository.save(category);
        CategoryResponse categoryResponse=categoryMapper.toResponse(savedCategory);
        return categoryResponse;

    }

    @Transactional
    public CategoryResponse  updateCategory(Integer id,CategoryDto categoryDto)
    {
        Category category=categoryRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));

        categoryMapper.updateEntityFromDto(categoryDto, category);

          category.setUpdatedAt(LocalDateTime.now());
          category.setUpdatedBy(1);

          Category updatedCategory = categoryRepository.save(category);

          return categoryMapper.toResponse(updatedCategory);
    }


    @Override
    public List<CategoryResponse> getAllCategories() {

       List<Category> categories=categoryRepository.findAllByIsDeletedFalse();
       return categoryMapper.toResponseList(categories);

    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories=categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categoryMapper.toResponseList(categories);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category =categoryRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse deleteCategory(Integer id) {
    Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

    category.setIsDeleted(true); // Mark as deleted
    category.setUpdatedAt(LocalDateTime.now());
    category.setUpdatedBy(1);

    Category updatedCategory = categoryRepository.save(category);
    return categoryMapper.toResponse(updatedCategory);
}

}
