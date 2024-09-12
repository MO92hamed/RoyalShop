package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Subcategory;
import com.ecommerce.royalshop.repositories.SubcategoryRepository;
import com.ecommerce.royalshop.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    @Override
    public Subcategory findSubcategoryById(Long id) {
        return subcategoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Subcategory with id [%s] is not found in our database", id)));
    }

    @Override
    public List<Subcategory> findAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    @Override
    public void deleteSubcategoryById(Long id) {
        subcategoryRepository.deleteById(id);
    }

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public Subcategory updateSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }
}
