package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.CategoryDTO;
import com.bloggio.api.bloggio.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/Create")
    public ResponseEntity<CategoryDTO> Create(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryDTO));
    }

    @GetMapping("/GetAll")
    public ResponseEntity<List<CategoryDTO>> GetAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }

    @GetMapping("/GetAllActivated")
    public ResponseEntity<List<CategoryDTO>> GetAllActivated() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllActivated());
    }

    @GetMapping("/GetAllDeactivated")
    public ResponseEntity<List<CategoryDTO>> GetAllDeactivated() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllDeactivated());
    }

    @PutMapping("/UpdateById")
    public void UpdateById(@RequestParam UUID categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateById(categoryId, categoryDTO);
    }
}
