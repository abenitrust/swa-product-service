package com.swa.application.service;

import com.swa.application.exception.DBException;
import com.swa.application.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.swa.application.domain.Product;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public void add(Product product) throws DBException {
        try {
            productRepository.save(product);
            logger.info(product + " created!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void update(Product product) throws DBException {
        try {
            productRepository.save(product);
            logger.info(product + " updated!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public Product findById(String productNumber) throws DBException {
        return productRepository.findById(productNumber)
                .orElseThrow(() -> {
                    String msg = "Product with product number " + productNumber + " not found!";
                    logger.error(msg);
                    return new DBException(msg);
                });
    }

    public List<Product> findAll()  throws DBException{
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void delete(String productNumber) throws DBException{
        try {
            var product = productRepository.findById(productNumber).orElseThrow(
                    () -> new DBException("Product by number " + productNumber + " not found!")
            );
            productRepository.delete(product);
            logger.info(product + " deleted!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }
}
