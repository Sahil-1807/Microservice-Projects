package com.example.orderservice.services;

import com.example.orderservice.clients.InventoryClient;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.events.OrderPlacedEvent;
import com.example.orderservice.models.Order;
import com.example.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;  //@autowired is not required due to final and RequiredArgsConstrutor
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void placeOrder(OrderRequest orderRequest){
        var isPorductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isPorductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);

            // send message to kafka topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.email());
            logger.info("Start - Sending OrderPlaceEvent {} to Kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            logger.info("End - Sending OrderPlaceEvent {} to Kafka topic order-placed", orderPlacedEvent);
        }
        else{
            throw new RuntimeException("Product with skuCode: " + orderRequest.skuCode() + " is not in the stock");
        }

    }
}

