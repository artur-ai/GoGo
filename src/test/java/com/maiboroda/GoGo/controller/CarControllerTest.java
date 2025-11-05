package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
public class CarControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testReturnAllCars() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].brand", notNullValue()))
                .andExpect(jsonPath("$[0].model", notNullValue()))
                .andExpect(jsonPath("$[0].imageUrl", startsWith("https://res.cloudinary.com/")));
    }

    @Test
    void testReturnSkodaFabia() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].model", hasItem("Fabia")))
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].year", hasItem(2013)))
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].pricePerDay", hasItem(600.0)));
    }

    @Test
    void testReturnAllCarBrands() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder("Skoda", "Ravon", "Volkswagen", "Ford","Skoda","Volkswagen", "Nissan", "Audi", "Skoda", "BMW", "Toyota", "Hyundai")));
    }
}
