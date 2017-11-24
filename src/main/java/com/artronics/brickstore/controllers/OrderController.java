package com.artronics.brickstore.controllers;

import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers/{customerId}/orders")
public class OrderController {
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "/{orderId}")
    public void getOrder(@PathVariable Long orderId) {

    }

    @GetMapping
    public void getOrders() {

    }
}
