package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Customer;
import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

public class BaseControllerTest {
    protected MockMvc mockMvc;

    @InjectMocks
    protected OrderController orderController;

    @Mock
    protected OrderRepository orderRepository;
    @Mock
    protected CustomerRepository customerRepository;

    protected Customer customer;
    protected Order order;

    protected void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        customer = new Customer(1L, "John");
        order = new Order();
        order.setId(123L);

        // findOne(1) always returns customer, if not we redefine it inside test
        when(customerRepository.findOne(1L)).thenReturn(customer);
        // same as orderRepo but for 123
        when(orderRepository.findOne(123L)).thenReturn(order);
    }
}
