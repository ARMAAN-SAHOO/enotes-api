package com.armaan.enotes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.armaan.enotes.dto.CategoryDto;
import com.armaan.enotes.dto.CategoryResponse;
import com.armaan.enotes.service.CategoryService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryDto categoryDto) {
        CategoryResponse savedCategory=categoryService.createCategory(categoryDto);

        URI location=ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCategory.getId())
                    .toUri();

        return ResponseEntity.created(location).body(savedCategory);
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        CategoryResponse categoryResponse=categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(categoryResponse);
    }
    
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
            List<CategoryResponse> categories=categoryService.getAllCategories();
        if(categories.isEmpty()){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
            List<CategoryResponse> categories=categoryService.getActiveCategory();
        if(categories.isEmpty()){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        CategoryResponse categoryResponse= categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategoryById(@PathVariable Integer id) {
        CategoryResponse categoryResponse= categoryService.deleteCategory(id);
        return ResponseEntity.ok(categoryResponse);
        
    }

}
