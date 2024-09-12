package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Category;
import com.ecommerce.royalshop.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.findCategoryById(id);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.findAllCategories();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        categoryService.deleteCategoryById(id);
        try {
            response.put("Deleted Successfully", Boolean.TRUE);
            return response;
        }catch (Exception e){
            response.put("Failed to delete", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category category1 = categoryService.findCategoryById(id);
        if (category1 != null){
            return categoryService.updateCategory(category);
        }else
            throw new RuntimeException("Failed to update this category");
    }
}
