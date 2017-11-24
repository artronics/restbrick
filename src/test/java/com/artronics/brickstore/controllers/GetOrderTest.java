package com.artronics.brickstore.controllers;

import com.artronics.brickstore.repositories.CustomerRepository;
import com.artronics.brickstore.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class GetOrderTest {
    protected MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    protected OrderRepository orderRepository;
    @Mock
    protected CustomerRepository customerRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void it_should_send_ok_status_for_single_order() throws Exception {

    }

    @Test
    public void it_should_send_ok_status_for_all_orders() throws Exception {

    }

    @Test
    public void with_GET_it_returns_404_if_customer_does_not_exist() throws Exception {

    }
}
