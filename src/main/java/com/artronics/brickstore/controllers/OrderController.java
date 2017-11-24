package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Customer;
import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @PostMapping(path = "/customers/{customerId}/orders/{orderId}/dispatch")
    public ResponseEntity dispatchOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId) {

        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDoesNotExist(customerId)) {
            return ResponseEntity.notFound().build();
        }

        order.setDispatched(true);
        orderRepository.save(order);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/customers/{customerId}/orders/{orderId}")
    public ResponseEntity updateOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId,
            @RequestBody Order order) {

        Order oldOrder = orderRepository.findOne(orderId);
        if (oldOrder == null) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerRepository.findOne(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.isDispatched()) {
            return ResponseEntity.badRequest().build();
        }

        // we just need to setId of order which comes from request
        order.setId(order.getId());
        // we also need customer ref
        order.setCustomer(customer);
        orderRepository.save(order);

        // set Location to new order
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(orderId).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, location);

        return ResponseEntity.ok().headers(headers).build();
    }

    private boolean customerDoesNotExist(Long id) {
        Customer customer = customerRepository.findOne(id);
        return customer == null;
    }
}
