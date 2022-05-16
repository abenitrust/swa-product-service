package com.application.repository;

import com.application.domain.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductNumber(String productNumber);
}
