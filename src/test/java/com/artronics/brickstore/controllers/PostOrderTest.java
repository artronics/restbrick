package com.artronics.brickstore.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

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

}
