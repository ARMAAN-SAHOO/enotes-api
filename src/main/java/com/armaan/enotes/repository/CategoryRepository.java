package com.armaan.enotes.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armaan.enotes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category ,Integer>{

    List<Category> findByIsActiveTrueAndIsDeletedFalse();
    
}
