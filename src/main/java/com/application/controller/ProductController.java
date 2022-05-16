package com.application.controller;

import com.application.domain.Product;
import com.application.domain.Products;
import com.application.domain.Quantity;
import com.application.exception.CustomErrorType;
import com.application.integration.EventListener;
import com.application.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @GetMapping("/{productNumber}")
    public ResponseEntity<?> getProductByNumber(@PathVariable String productNumber) {
        log.info("GET request for /products/" + productNumber);
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        log.info("POST request for /products/ with body: " + product);
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        log.info("GET request for /products/");
        Products allProducts = new Products(productService.findAll());
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/{productNumber}")
    public ResponseEntity<?> deleteProductByNumber(@PathVariable String productNumber) {
        log.info("DELETE request for /products/" + productNumber);
    
        productService.deleteProduct(productNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productNumber}")
    public ResponseEntity<?> updateProductByNumber(@PathVariable String productNumber, @RequestBody Product product) {
        log.info("PUT request for /products/" + productNumber + " with body: " + product);
        Product oldProduct = productService.findProductByNumber(productNumber);
        if (oldProduct == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{productNumber}/quantity")
    public ResponseEntity<?> getProductQuantityByNumber(@PathVariable String productNumber) {
        log.info("GET request for /products/" + productNumber + "/quantity");
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.getNumberInStock(), HttpStatus.OK);
    }
}
