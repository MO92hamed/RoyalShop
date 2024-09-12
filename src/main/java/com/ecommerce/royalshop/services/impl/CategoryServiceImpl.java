package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Category;
import com.ecommerce.royalshop.repositories.CategoryRepository;
import com.ecommerce.royalshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Category with id [%s] is not found in our database", id)));
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
}
