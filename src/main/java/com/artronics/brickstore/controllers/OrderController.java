package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Customer;
import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<Order> getOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        if (customerDoesNotExist(customerId)) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }


        return new ResponseEntity(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@PathVariable Long customerId) {
        if (customerDoesNotExist(customerId)) {
            return ResponseEntity.notFound().build();
        }

        List<Order> orders = orderRepository.findAll();

        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    private boolean customerDoesNotExist(Long id) {
        Customer customer = customerRepository.findOne(id);
        return customer == null;
    }
}
