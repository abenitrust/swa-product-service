package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.application.domain.Product;
import com.application.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductByNumber(String productNumber) {
        return productRepository.findByProductNumber(productNumber);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteProduct(String productNumber) {
        var product = productRepository.findByProductNumber(productNumber);
        if(product != null) {
            productRepository.delete(product);
        }
    }
}
