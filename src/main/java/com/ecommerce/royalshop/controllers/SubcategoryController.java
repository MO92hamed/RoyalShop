package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Category;
import com.ecommerce.royalshop.models.Subcategory;
import com.ecommerce.royalshop.services.impl.CategoryServiceImpl;
import com.ecommerce.royalshop.services.impl.SubcategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {
    private final CategoryServiceImpl categoryService;
    private final SubcategoryServiceImpl subcategoryService;

    @GetMapping("/{id}")
    public Subcategory getSubcategoryById(@PathVariable Long id){
        return subcategoryService.findSubcategoryById(id);
    }

    @GetMapping
    public List<Subcategory> getAllSubs(){
        return subcategoryService.findAllSubcategories();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSubById(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        subcategoryService.deleteSubcategoryById(id);
        try{
            response.put("Subcategory deleted", Boolean.TRUE);
            return response;
        }catch(Exception e){
            response.put("Failed to delete subcategory", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping("/{idcategory}")
    public Subcategory addSubcategory(@RequestBody Subcategory subcategory, @PathVariable Long idcategory){
        Category category = categoryService.findCategoryById(idcategory);
        subcategory.setCategory(category);
        return subcategoryService.createSubcategory(subcategory);
    }

    @PutMapping("/{id}")
    public Subcategory updateSubcategory(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        Subcategory subcategory1 = subcategoryService.findSubcategoryById(id);

        if (subcategory1 != null) {
            subcategory.setCategory(subcategory1.getCategory());
            return subcategoryService.updateSubcategory(subcategory);

        } else {
            throw new RuntimeException("Failed to update subcategory");

        }
    }

}
