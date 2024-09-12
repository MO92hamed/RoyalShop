package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Product;

import java.util.List;

public interface ProductService {
    Product findProductById(Long id);
    List<Product> findAllProducts();
    void deleteProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
}
