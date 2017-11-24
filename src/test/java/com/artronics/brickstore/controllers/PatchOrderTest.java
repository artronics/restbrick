package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.entities.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatchOrderTest extends BaseControllerTest {
    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void it_should_send_ok_status() throws Exception {
        mockMvc.perform(
                patch("/customers/1/orders/123")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andExpect(status().isOk());
    }

    @Test
    public void it_should_send_404_if_order_does_not_exist() throws Exception {
        mockMvc.perform(
                patch("/customers/1/orders/8273482")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_should_send_404_if_customer_does_not_exist() throws Exception {
        mockMvc.perform(
                patch("/customers/1456/orders/123")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_should_update_the_order() throws Exception {
        // first we create one order with zero size OrderItems
        order.setOrderItems(new LinkedList<>());
        when(orderRepository.findOne(123L)).thenReturn(order);

        // with new order we add one item to OrderItem list
        Order newOrder = new Order();
        newOrder.setOrderItems(new LinkedList<>(Arrays.asList(new OrderItem())));
        String jsonOrder = gson.toJson(newOrder);

        doAnswer((Answer<Order>) invocation -> {
            final Object[] args = invocation.getArguments();
            Order order = (Order) args[0];
            assertThat(order.getOrderItems().size()).isEqualTo(1);
            return order;
        }).when(orderRepository).save(any(Order.class));

        mockMvc.perform(
                patch("/customers/1/orders/123")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder));
    }

    @Test
    public void it_should_send_Location_of_resource() throws Exception {
        MvcResult result = mockMvc.perform(
                patch("/customers/1/orders/123")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isEqualTo("http://localhost/customers/1/orders/123");
    }
}
