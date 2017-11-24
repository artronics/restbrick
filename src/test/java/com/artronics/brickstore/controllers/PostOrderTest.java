package com.artronics.brickstore.controllers;

import com.artronics.brickstore.entities.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostOrderTest extends BaseControllerTest {
    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void it_should_send_created_status() throws Exception {
        this.mockMvc.perform(
                post("/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andExpect(status().isCreated());
    }

    @Test
    public void it_should_call_save_method_on_orderRepository() throws Exception {
        mockMvc.perform(
                post("/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andReturn();

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    public void it_should_set_customer_id_before_saving_order() throws Exception {
        doAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                final Object[] args = invocation.getArguments();
                Order order = (Order) args[0];
                assertThat(order.getCustomer().getId()).isEqualTo(1L);
                return order;
            }
        }).when(orderRepository).save(any(Order.class));

        mockMvc.perform(
                post("/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder));

    }

    @Test
    public void it_should_return_NOT_FOUND_if_user_does_not_exist() throws Exception {
        when(customerRepository.findOne(1L)).thenReturn(null);

        this.mockMvc.perform(
                post("/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_should_return_location_of_created_resource() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonOrder))
                .andReturn();

        // then
        String location = result.getResponse().getHeader("Location");
        assertThat(location).isEqualTo("http://localhost/customers/1/orders/123");
    }

}
