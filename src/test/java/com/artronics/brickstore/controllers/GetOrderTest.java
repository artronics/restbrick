package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetOrderTest extends BaseControllerTest {
    @Before
    @Override
    public void setup() {
        super.setup();
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
        mockMvc.perform(get("/customers/332/orders"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOrder_should_send_one_resource() throws Exception {
        order.setOrderItems(new LinkedList<>());
        when(orderRepository.findOne(123L)).thenReturn(order);

        mockMvc.perform(get("/customers/1/orders/123"))
                .andExpect(jsonPath("$.orderItems").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void if_order_does_not_exist_it_should_send_404() throws Exception {
        mockMvc.perform(get("/customers/1/orders/747467"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllOrders_should_send_all_orders() throws Exception {
        List<Order> orders = new LinkedList<>(Arrays.asList(new Order(), new Order()));
        when(orderRepository.findAll()).thenReturn(orders);
        // then
        mockMvc.perform(get("/orders"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }
}
