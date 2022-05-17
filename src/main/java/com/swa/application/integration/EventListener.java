package com.swa.application.integration;

import com.swa.application.domain.Order;
import com.swa.application.domain.Product;
import com.swa.application.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class EventListener {
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @KafkaListener(topics = "${event.topics.order-placed}")
    public void receiveOrderEvent(Order order) {
        if(order == null) {
            log.error("Invalid order " + order);
            return;
        }
        order.getOrderLines().stream().forEach(orderLine -> {
            String productNumber = orderLine.getProduct().getProductNumber();
            try {
                var product = productService.findById(productNumber);
                product.setNumberInStock(product.getNumberInStock() - orderLine.getQuantity() );
                productService.update(product);
            } catch (Exception e) {
                // Error occurred while decreasing product stock
                // TODO
                log.error(
                        "Error occurred while decreasing product stock "
                        + productNumber + ". "
                        + e.getMessage()
                );
            }
        });
    }
}
