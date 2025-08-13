package com.armaan.enotes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.armaan.enotes.dto.CategoryDto;
import com.armaan.enotes.dto.CategoryResponse;
import com.armaan.enotes.entity.Category;
import com.armaan.enotes.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        Boolean saveCategory=categoryService.saveCategory(category);
        if(saveCategory){
            return new ResponseEntity<>("saved",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/")
    public ResponseEntity<CategoryDto> getAllCategories() {
            List<Category> categories=categoryService.getAllCategories();
        if(CollectionUtils.isEmpty(categories)){
            return  ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping("/active")
    public ResponseEntity<CategoryDto> getA() {
            List<Category> categories=categoryService.getAllCategories();
        if(CollectionUtils.isEmpty(categories)){
            return  ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        CategoryDto categoryDto= categoryService.findCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategoryById(@PathVariable Integer id) {
        boolean result= categoryService.deleteById(id);
        
    }

}
