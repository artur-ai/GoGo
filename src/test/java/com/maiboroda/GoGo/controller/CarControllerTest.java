package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder("Skoda", "Ravon", "Volkswagen", "Ford", "Skoda", "Volkswagen", "Nissan", "Audi", "Skoda", "BMW", "Toyota", "Hyundai")));
    }

    @Test
    void testReturnCarsWithTag() throws Exception {
        mockMvc.perform(get("/api/cars")
                        .param("tagIds", "1", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[?(@.brand == 'Audi')].model", hasItem("A4")))
                .andExpect(jsonPath("$[?(@.brand == 'Audi')].year", hasItem(2017)))
                .andExpect(jsonPath("$[?(@.brand == 'Audi')].pricePerDay", hasItem(2600.0)));
    }

    @Test
    void testReturnPremiumCars() throws Exception {
        mockMvc.perform(get("/api/cars")
                        .param("tagIds", "7")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[?(@.brand == 'Toyota')].model", hasItem("Camry")))
                .andExpect(jsonPath("$[?(@.brand == 'Toyota')].year", hasItem(2019)))
                .andExpect(jsonPath("$[?(@.brand == 'Toyota')].pricePerDay", hasItem(2600.0)))

                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].model", hasItem("Octavia")))
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].year", hasItem(2020)))
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].pricePerDay", hasItem(2600.0)))

                .andExpect(jsonPath("$[?(@.brand == 'Volkswagen')].model", hasItem("Jetta")))
                .andExpect(jsonPath("$[?(@.brand == 'Volkswagen')].year", hasItem(2015)))
                .andExpect(jsonPath("$[?(@.brand == 'Volkswagen')].pricePerDay", hasItem(1250.0)));
    }

    @Test
    void testReturnCarsWithNoTags() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[?(@.brand == 'Skoda')].model", hasItem("Fabia")))
                .andExpect(jsonPath("$[?(@.brand == 'Volkswagen')].model", hasItem("Jetta")));
    }


}
