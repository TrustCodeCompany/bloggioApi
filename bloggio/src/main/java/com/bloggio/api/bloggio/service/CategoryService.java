package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.CategoryDTO;
import com.bloggio.api.bloggio.mapper.CategoryMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Category;
import com.bloggio.api.bloggio.persistence.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;

import com.bloggio.api.bloggio.exception.Exception;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapperImpl categoryMapperImpl;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapperImpl categoryMapperImpl) {
        this.categoryRepository = categoryRepository;
        this.categoryMapperImpl = categoryMapperImpl;
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = categoryMapperImpl.categoryDTOToCategory(categoryDTO);
        log.info("Create Category Successful");
        return categoryMapperImpl.categoryToCategoryDTO(categoryRepository.save(category));
    }

    public List<CategoryDTO> getAll() {
        log.info("Get All Category");
        return categoryMapperImpl.ListCategoryToListCategoryDTO(categoryRepository.findAll());
    }

    public List<CategoryDTO> getAllActivated() {
        log.info("Get All Activated Category");
        return categoryMapperImpl.ListCategoryToListCategoryDTO(
                categoryRepository.findAll().stream().filter(item -> item.getCategoryState() == 1)
                        .collect(Collectors.toList()));
    }

    public List<CategoryDTO> getAllDeactivated() {
        log.info("Get All Deactivated Category");
        return categoryMapperImpl.ListCategoryToListCategoryDTO(categoryRepository.findAll().stream()
                .filter(item -> item.getCategoryState() == 0).collect(Collectors.toList()));
    }

    public void updateById(UUID categoryId, CategoryDTO categoryDTO) {
        Optional<Category> findCategoryById = categoryRepository.findById(categoryId);
        if (findCategoryById.isEmpty()) {
            log.error("Error");
            throw new Exception("Category Not Found", HttpStatus.NOT_FOUND);
        }
        Category updaCategory = findCategoryById.get();
        updaCategory.setCategoryName(categoryDTO.getCategoryName());
        updaCategory.setCategoryDesc(categoryDTO.getCategoryDesc());
        updaCategory.setCategoryImage(categoryDTO.getCategoryImage());
        updaCategory.setCategoryState(categoryDTO.getCategoryState());
        log.info("Update Successful");
        categoryRepository.save(updaCategory);
        throw new Exception("Category Update Successful", HttpStatus.OK);
    }

}
