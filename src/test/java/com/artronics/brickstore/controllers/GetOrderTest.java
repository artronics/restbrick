package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Customer;
import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetOrderTest {
    protected MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    protected OrderRepository orderRepository;
    @Mock
    protected CustomerRepository customerRepository;

    Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        customer = new Customer(1L, "John");

        // findOne(1) always returns customer, if not we redefine it inside test
        when(customerRepository.findOne(1L)).thenReturn(customer);
    }

    @Test
    public void it_should_send_ok_status_for_single_order() throws Exception {
        mockMvc.perform(get("/customers/1/orders/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void it_should_send_ok_status_for_all_orders() throws Exception {
        mockMvc.perform(get("/customers/1/orders/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void with_GET_single_it_returns_404_if_customer_does_not_exist() throws Exception {
        when(customerRepository.findOne(1L)).thenReturn(null);
        // then
        mockMvc.perform(get("/customers/1/orders/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void with_GET_list_resource_it_returns_404_if_customer_does_not_exist() throws Exception {
        when(customerRepository.findOne(1L)).thenReturn(null);
        // then
        mockMvc.perform(get("/customers/1/orders"))
                .andExpect(status().isNotFound());
    }
}
