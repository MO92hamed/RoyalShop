package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Product;
import com.ecommerce.royalshop.models.Subcategory;
import com.ecommerce.royalshop.services.impl.ProductServiceImpl;
import com.ecommerce.royalshop.services.impl.SubcategoryServiceImpl;
import com.ecommerce.royalshop.utils.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final SubcategoryServiceImpl subcategoryService;
    private final StorageService storageService;
    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.findAllProducts();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            productService.deleteProductById(id);

            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch(Exception e){
            response.put("Failed", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping("/subcategory/{idsubcategory}")
    public Product addProduct(Product product,
                              @PathVariable Long idsubcategory,
                              @RequestParam MultipartFile file){
        /*storageService.store(file);
        product.setImage(file.getOriginalFilename());*/
        try{
            String filename = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + filename + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            product.setImage(original);

            Subcategory subcategory = subcategoryService.findSubcategoryById(idsubcategory);
            product.setSubcategory(subcategory);

        }catch(Exception e){
            throw new RuntimeException("Fail file Problem Backend !!");
        }
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
        Product p1 = productService.findProductById(id);
        if (p1 != null){
            product.setSubcategory(p1.getSubcategory());
            return productService.updateProduct(product);
        }
        else
            throw new RuntimeException("FAIL!!");
    }

    //affiche Image
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = storageService.LoadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }

}
