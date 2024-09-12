package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Product;
import com.ecommerce.royalshop.repositories.ProductRepository;
import com.ecommerce.royalshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public Product findProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Product with id [%s] is not found in our database", id)));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
