package com.bloggio.api.bloggio.persistence.repository;

import com.bloggio.api.bloggio.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByCategoryDesc(String categoryDesc);

}
