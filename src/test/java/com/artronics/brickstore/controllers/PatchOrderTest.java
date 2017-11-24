package com.artronics.brickstore.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

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
}
