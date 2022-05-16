package com.application.integration;

import com.application.domain.Product;
import com.application.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@RefreshScope
@Service
public class EventListener {
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @KafkaListener(topics = "event")
    public void receiveOrderEvent(OrderProductsDto orderProducts) {
        orderProducts.orderProducts.stream().forEach(prod -> {
            var product = productService.findProductByNumber(prod.getProductNumber());
            if(product == null) {
                // invalid product, do something about it.
            }
            product.setNumberInStock(product.getNumberInStock() - prod.getQuantity());
            productService.save(product);

        });
    }
}
