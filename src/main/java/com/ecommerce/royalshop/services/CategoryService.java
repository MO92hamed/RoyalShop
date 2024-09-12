package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Category;

import java.util.List;

public interface CategoryService {
    Category findCategoryById(Long id);
    List<Category> findAllCategories();
    void deleteCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Category category);
}
