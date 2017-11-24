package com.artronics.brickstore.integration;

import com.artronics.brickstore.BrickstoreApplication;
import com.artronics.brickstore.entities.Order;
import com.artronics.brickstore.entities.OrderItem;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BrickstoreApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Before
    public void setup() {

    }

    @Test
    public void getResources_will_return_all_orders_with_detail() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(jsonPath("$[0].orderItems[0]").exists());
    }

    @Test
    public void createOrder() throws Exception {
        Order order = makeOrder(false);
        String json = gson.toJson(order);

        MvcResult result = mockMvc.perform(post("/customers/1/orders")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();

        String headerValue = result.getResponse().getHeader("Location");
        System.out.println(headerValue);
        assertThat(headerValue.isEmpty()).isEqualTo(false);
    }

    @Test
    public void updateOrder() throws Exception {
        Order order = makeOrder(false);
        String json = gson.toJson(order);

        // order id=1 is not dispatched. see data.sql
        MvcResult result = mockMvc.perform(patch("/customers/1/orders/1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void preventToUpdateDispatchedOrder() throws Exception {
        Order order = makeOrder(true);
        String json = gson.toJson(order);
        MvcResult result = mockMvc.perform(patch("/customers/1/orders/2")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    private Order makeOrder(boolean dispatched) {
        Order order = new Order();
        order.setDispatched(dispatched);
        OrderItem item = new OrderItem(25, "brick", BigDecimal.valueOf(23.4));
        order.setOrderItems(new LinkedList<>(Arrays.asList(item)));

        return order;
    }

}
