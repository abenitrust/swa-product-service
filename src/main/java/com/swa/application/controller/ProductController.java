package com.swa.application.controller;

import com.swa.application.domain.Product;
import com.swa.application.integration.EventListener;
import com.swa.application.service.ProductService;

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
        try {
            return new ResponseEntity<>( productService.findById(productNumber), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Product product) {
        try {
            productService.add(product);
            return new ResponseEntity<>("Success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>( productService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productNumber}")
    public ResponseEntity<?> delete(@PathVariable String productNumber) {
        try {
            productService.delete(productNumber);
            return new ResponseEntity<>("Success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Product product) {
        try {
            productService.update(product);
            return new ResponseEntity<>("Success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
