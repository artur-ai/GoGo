package com.maiboroda.GoGo.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DBRider
@DataSet("datasets/cars.yml")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

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
    void testReturnRandomCars() throws Exception {
        mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {3.70, 2.60, 5.50, 6.10})
    void testExistCarsPricePerMinute(double price) throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].pricePerMinute", hasItem(price)));
    }

    @ParameterizedTest
    @ValueSource(ints = {2017, 2020, 2015, 2014, 2018, 2019})
    void testExistCarsYear(int year) throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].year", hasItem(year)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Petrol", "Diesel", "Electro", "Petrol/Gas"})
    void testExistCarsFuelTypes(String fuelType) throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].fuelType", hasItem(fuelType)));
    }

    @Test
    void testAllCarsHaveNumericPrices() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pricePerMinute", isA(Number.class)))
                .andExpect(jsonPath("$[0].pricePerDay", isA(Number.class)));
    }

    @Test
    void testNoCarHasZeroPrice() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.pricePerMinute <= 0)]", hasSize(0)));
    }

    @Test
    void testRandomCarsHaveCorrectStructure() throws Exception {
        mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", isA(Integer.class)))
                .andExpect(jsonPath("$[0].createdAt", notNullValue()));
    }

    @Test
    void testRandomCarsHaveCompleteFields() throws Exception {
        mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fuelType", notNullValue()))
                .andExpect(jsonPath("$[0].engine", notNullValue()))
                .andExpect(jsonPath("$[0].createdAt", notNullValue()));
    }

    @Test
    void testRandomResultsAreNotIdentical() throws Exception {
        String result1 = mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String result2 = mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotEquals(result1, result2);
    }

    @Test
    void testNegativeRandomValue() throws Exception {
        ReflectionTestUtils.setField(carService, "randomNumber", -3);

        mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBiggerNumerValueThanCarsInDb() throws Exception {
        ReflectionTestUtils.setField(carService, "randomNumber", 100);

        mockMvc.perform(get("/api/cars/random"))
                .andExpect(status().isBadRequest());
    }
}
