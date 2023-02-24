package com.bloggio.api.bloggio.mapper;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.bloggio.api.bloggio.dto.CategoryDTO;
import com.bloggio.api.bloggio.persistence.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    CategoryDTO categoryToCategoryDTO(Category category);

    List<Category> ListCategoryDTOToListCategory(List<CategoryDTO> ListCategoryDTO);

    List<CategoryDTO> ListCategoryToListCategoryDTO(List<Category> ListCategory);

}
