package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Customer;
import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class OrderController {
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "/customers/{customerId}/orders/{orderId}")
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

    @GetMapping(path = "/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(path = "/customers/{customerId}/orders")
    public ResponseEntity createOrder(@PathVariable Long customerId, @RequestBody Order order) {
        Customer customer = customerRepository.findOne(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        order.setCustomer(customer);
        Order newOrder = orderRepository.save(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newOrder.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(path = "/customers/{customerId}/orders/{orderId}")
    public ResponseEntity updateOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId,
            @RequestBody Order order) {

        return ResponseEntity.ok().build();
    }

    private boolean customerDoesNotExist(Long id) {
        Customer customer = customerRepository.findOne(id);
        return customer == null;
    }
}
