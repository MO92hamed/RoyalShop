package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Subcategory;

import java.util.List;

public interface SubcategoryService {
    Subcategory findSubcategoryById(Long id);
    List<Subcategory> findAllSubcategories();
    void deleteSubcategoryById(Long id);
    Subcategory createSubcategory(Subcategory subcategory);
    Subcategory updateSubcategory(Subcategory subcategory);
}
