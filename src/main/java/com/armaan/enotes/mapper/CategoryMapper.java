package com.armaan.enotes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.armaan.enotes.dto.CategoryDto;
import com.armaan.enotes.dto.CategoryResponse;
import com.armaan.enotes.entity.Category;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {


       @Mapping(target = "isDeleted", ignore = true)
       Category toEntity(CategoryDto categoryDto);
       
       CategoryDto toDto(Category category);

       CategoryResponse toResponse(Category entity);

       List<Category> toEntityList(List<CategoryDto> categoryDtos);

       List<CategoryDto> toDtoList(List<Category> entities);

       List<CategoryResponse> toResponseList(List<Category> entities);

       @Mapping(target = "isDeleted", ignore = true)
       void updateEntityFromDto(CategoryDto dto,@MappingTarget Category category);
       
}
