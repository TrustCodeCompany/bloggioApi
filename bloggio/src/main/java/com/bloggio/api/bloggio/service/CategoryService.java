package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.CategoryDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.mapper.CategoryMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Category;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapperImpl categoryMapper;
    public CategoryService(CategoryRepository categoryRepository, CategoryMapperImpl categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category categorySave = null;

        try {
            Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
            categorySave = categoryRepository.save(category);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return categoryMapper.categoryToCategoryDTO(categorySave);
    }

}
