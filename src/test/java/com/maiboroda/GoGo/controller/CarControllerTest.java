package com.maiboroda.GoGo.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    private CarRequestDto createVaidCarRequestDto() {
        return new CarRequestDto("Dodge", "Dart", 2014, "Petrol", "2.4",
                new BigDecimal("15.50"), new BigDecimal("1200.00"), new BigDecimal("10.00"),
                "https://test.com/dodge.png");
    }

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
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford",
                        "Skoda", "Volkswagen", "Nissan", "Audi",
                        "Skoda", "BMW", "Toyota", "Hyundai")));
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

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true)
    void testAddValidCarReturen201Created() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();

        mockMvc.perform(post("/api/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.brand", is("Dodge")))
                .andExpect(jsonPath("$.pricePerDay", is(1200.00)));
    }

    @Test
    void testAddCarInvalidYear_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();
        requestDto.setYear(3000);

        mockMvc.perform(post("/api/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors.year", is("Year must be before 2026")));
    }

    @Test
    void testAddCarMissingBrand_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();
        requestDto.setBrand("");

        mockMvc.perform(post("/api/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.brand", is("Brand can not be empty")));
    }

    @Test
    void testAddCarNonPositivePrice_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();
        requestDto.setPricePerMinute(BigDecimal.ZERO);

        mockMvc.perform(post("/api/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.pricePerMinute", is("Price per minute must be positive")));
    }

    @Test
    void testFindCarsByCountry_Ukraine_Returns6Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Ukraine"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford", "BMW", "Hyundai")));
    }

    @Test
    void testFindCarsByCountry_Poland_Returns7Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Poland"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford", "Volkswagen", "Hyundai", "Skoda")));
    }

    @Test
    void testFindCarsByCountry_Germany_Returns7Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Germany"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].brand", hasItem("Audi")))
                .andExpect(jsonPath("$[*].brand", hasItem("Nissan")));
    }

    @Test
    void testFindCarsByCountry_France_Returns8Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "France"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void testFindCarsByCountry_Spain_Returns4Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Spain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Ford", "Skoda", "Toyota", "Hyundai")));
    }

    @Test
    void testFindCarsByCountry_Netherlands_Returns3Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Netherlands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Volkswagen", "Nissan", "Audi")));
    }

    @Test
    void testFindCarsByCountry_Japan_Returns3Cars() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Japan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].brand", hasItem("Toyota")))
                .andExpect(jsonPath("$[*].model", hasItem("Leaf")));
    }

    @Test
    void testFindCarsByCountry_ReturnsCarWithAllRequiredFields() throws Exception {
        mockMvc.perform(get("/api/cars/country")
                        .param("countryName", "Ukraine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].brand", notNullValue()))
                .andExpect(jsonPath("$[0].model", notNullValue()))
                .andExpect(jsonPath("$[0].year", notNullValue()))
                .andExpect(jsonPath("$[0].fuelType", notNullValue()))
                .andExpect(jsonPath("$[0].engine", notNullValue()))
                .andExpect(jsonPath("$[0].pricePerMinute", isA(Number.class)))
                .andExpect(jsonPath("$[0].pricePerDay", isA(Number.class)))
                .andExpect(jsonPath("$[0].insurancePrice", isA(Number.class)))
                .andExpect(jsonPath("$[0].imageUrl", startsWith("https://")))
                .andExpect(jsonPath("$[0].createdAt", notNullValue()));
    }

    @Test
    void testFindCarsByCountry_HyundaiH1_InMultipleCountries() throws Exception {
        String[] countries = {"Ukraine", "Poland", "Germany", "France", "Italy", "Spain"};

        for (String country : countries) {
            mockMvc.perform(get("/api/cars/country")
                            .param("countryName", country))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[?(@.brand == 'Hyundai' && @.model == 'H-1')]", hasSize(1)));
        }
    }
}

