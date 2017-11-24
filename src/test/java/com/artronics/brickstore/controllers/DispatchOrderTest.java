package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DispatchOrderTest extends BaseControllerTest {
    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void it_should_send_ok_status() throws Exception {
        mockMvc.perform(post("/customers/1/orders/123/dispatch"))
                .andExpect(status().isOk());
    }

    @Test
    public void it_should_send_BadRequest_if_order_does_not_exist() throws Exception {
        mockMvc.perform(post("/customers/1/orders/8478/dispatch"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void it_should_send_NotFound_if_customer_does_not_exist() throws Exception {
        mockMvc.perform(post("/customers/134/orders/123/dispatch"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_should_update_order_based_on_dispatch_value() throws Exception {
        mockMvc.perform(post("/customers/134/orders/123/dispatch"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_should_set_dispatchValue_to_true() throws Exception {
        doAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                final Object[] args = invocation.getArguments();
                Order order = (Order) args[0];
                assertThat(order.isDispatched()).isEqualTo(true);
                return order;
            }
        }).when(orderRepository).save(any(Order.class));

        mockMvc.perform(post("/customers/134/orders/123/dispatch"));
    }

}
