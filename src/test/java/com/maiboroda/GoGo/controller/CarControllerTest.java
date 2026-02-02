package com.maiboroda.GoGo.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import com.maiboroda.GoGo.service.CarService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CarRequestDto createVaidCarRequestDto() {
        return new CarRequestDto("Dodge", "Dart", 2014, "Petrol", "2.4",
                new BigDecimal("15.50"), new BigDecimal("1200.00"), new BigDecimal("10.00"),
                "https://test.com/dodge.png");
    }


    @Test
    void testReturnAllCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(12)))
                .andExpect(jsonPath("$.content[0].brand", notNullValue()));
    }

    @Test
    void testReturnSkodaFabia() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.brand == 'Skoda')].model", hasItem("Fabia")))
                .andExpect(jsonPath("$.content[?(@.brand == 'Skoda')].year", hasItem(2013)))
                .andExpect(jsonPath("$.content[?(@.brand == 'Skoda')].pricePerDay", hasItem(600.0)));
    }

    @Test
    void testReturnAllCarBrands() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford",
                        "Skoda", "Volkswagen", "Nissan", "Audi",
                        "Skoda", "BMW", "Toyota", "Hyundai")));
    }

    @Test
    void testReturnRandomCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {3.70, 2.60, 5.50, 6.10})
    void testExistCarsPricePerMinute(double price) throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].pricePerMinute", hasItem(price)));
    }

    @ParameterizedTest
    @ValueSource(ints = {2017, 2020, 2015, 2014, 2018, 2019})
    void testExistCarsYear(int year) throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].year", hasItem(year)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Petrol", "Diesel", "Electro", "Petrol/Gas"})
    void testExistCarsFuelTypes(String fuelType) throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].fuelType", hasItem(fuelType)));
    }

    @Test
    void testAllCarsHaveNumericPrices() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].pricePerMinute", isA(Number.class)))
                .andExpect(jsonPath("$.content[0].pricePerDay", isA(Number.class)));
    }

    @Test
    void testNoCarHasZeroPrice() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.pricePerMinute <= 0)]", hasSize(0)));
    }

    @Test
    void testRandomCarsHaveCorrectStructure() throws Exception {
        mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", isA(Integer.class)));
    }

    @Test
    void testRandomCarsHaveCompleteFields() throws Exception {
        mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fuelType", notNullValue()))
                .andExpect(jsonPath("$[0].engine", notNullValue()));
    }

    @Test
    void testRandomResultsAreNotIdentical() throws Exception {
        String result1 = mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String result2 = mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotEquals(result1, result2);
    }

    @Test
    void testNegativeRandomValue() throws Exception {
        ReflectionTestUtils.setField(carService, "randomNumber", -3);

        mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBiggerNumerValueThanCarsInDb() throws Exception {
        ReflectionTestUtils.setField(carService, "randomNumber", 100);

        mockMvc.perform(get("/api/v1/cars/random"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true)
    void testAddValidCarReturen201Created() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();

        mockMvc.perform(post("/api/v1/cars")
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

        mockMvc.perform(post("/api/v1/cars")
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

        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.brand", is("Brand can not be empty")));
    }

    @Test
    void testAddCarNonPositivePrice_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();
        requestDto.setPricePerMinute(BigDecimal.ZERO);

        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.pricePerMinute", is("Price per minute must be positive")));
    }

    @Test
    void testFindCarsByCountry_Ukraine_Returns6Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Ukraine"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford", "BMW", "Hyundai")));
    }

    @Test
    void testFindCarsByCountry_Poland_Returns7Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Poland"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Skoda", "Ravon", "Volkswagen", "Ford", "Volkswagen", "Hyundai", "Skoda")));
    }

    @Test
    void testFindCarsByCountry_Germany_Returns7Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Germany"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].brand", hasItem("Audi")))
                .andExpect(jsonPath("$[*].brand", hasItem("Nissan")));
    }

    @Test
    void testFindCarsByCountry_France_Returns8Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "France"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void testFindCarsByCountry_Spain_Returns4Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Spain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Ford", "Skoda", "Toyota", "Hyundai")));
    }

    @Test
    void testFindCarsByCountry_Netherlands_Returns3Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Netherlands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].brand", containsInAnyOrder(
                        "Volkswagen", "Nissan", "Audi")));
    }

    @Test
    void testFindCarsByCountry_Japan_Returns3Cars() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
                        .param("countryName", "Japan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].brand", hasItem("Toyota")))
                .andExpect(jsonPath("$[*].model", hasItem("Leaf")));
    }

    @Test
    void testFindCarsByCountry_ReturnsCarWithAllRequiredFields() throws Exception {
        mockMvc.perform(get("/api/v1/cars/country")
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
                .andExpect(jsonPath("$[0].imageUrl", startsWith("https://")));
    }

    @Test
    void testFindCarsByCountry_HyundaiH1_InMultipleCountries() throws Exception {
        String[] countries = {"Ukraine", "Poland", "Germany", "France", "Italy", "Spain"};

        for (String country : countries) {
            mockMvc.perform(get("/api/v1/cars/country")
                            .param("countryName", country))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[?(@.brand == 'Hyundai' && @.model == 'H-1')]", hasSize(1)));
        }
    }

    @Test
    void testUpdateCar_ShouldReturn200okAndUpdateCar() throws Exception {
        CarRequestDto updateCar = createVaidCarRequestDto();
        updateCar.setBrand("Skoda");
        updateCar.setModel("Fabia Updated");
        updateCar.setYear(2015);

        mockMvc.perform(put("/api/v1/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Skoda")))
                .andExpect(jsonPath("$.model", is("Fabia Updated")))
                .andExpect(jsonPath("$.year", is(2015)))
                .andExpect(jsonPath("$.engine", is("2.4")))
                .andExpect(jsonPath("$.pricePerDay", is(1200.0)));
    }

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true)
    void updateCarById_Success() throws Exception {
        CarRequestDto requestDto = new CarRequestDto();
        requestDto.setBrand("Updated Brand");
        requestDto.setModel("Updated Model");
        requestDto.setYear(2023);
        requestDto.setFuelType("Petrol");
        requestDto.setEngine("2.0");
        requestDto.setPricePerMinute(BigDecimal.valueOf(1.5));
        requestDto.setPricePerDay(BigDecimal.valueOf(50.0));
        requestDto.setInsurancePrice(BigDecimal.valueOf(10.0));
        requestDto.setImageUrl("http://example.com/updated.jpg");

        mockMvc.perform(put("/api/v1/cars/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Updated Brand"))
                .andExpect(jsonPath("$.model").value("Updated Model"));
    }

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true)
    void updateCarById_NotFound() throws Exception {
        CarRequestDto requestDto = createVaidCarRequestDto();
        requestDto.setBrand("Test");
        requestDto.setModel("Test");
        requestDto.setYear(2023);

        mockMvc.perform(put("/api/v1/cars/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCar_WithInvalidYear_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto updateRequest = createVaidCarRequestDto();
        updateRequest.setYear(1800);

        mockMvc.perform(put("/api/v1/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors.year", is("Year must be after 1930")));
    }

    @Test
    void testUpdateCar_WithEmptyBrand_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto updateRequest = createVaidCarRequestDto();
        updateRequest.setBrand("");

        mockMvc.perform(put("/api/v1/cars/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.brand", is("Brand can not be empty")));
    }

    @Test
    void testUpdateCar_WithNegativePrice_ShouldReturn400BadRequest() throws Exception {
        CarRequestDto updateRequest = createVaidCarRequestDto();
        updateRequest.setPricePerDay(new BigDecimal("-100"));

        mockMvc.perform(put("/api/v1/cars/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.pricePerDay", is("Price per day must be positive")));
    }

    @Test
    void testUpdateCar_NonExistentId_ShouldReturn404NotFound() throws Exception {
        CarRequestDto updateRequest = createVaidCarRequestDto();

        mockMvc.perform(put("/api/v1/cars/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Car not found by id: 999")));
    }

    @Test
    void testUpdateCar_AllFieldsUpdate_ShouldReturn200() throws Exception {
        CarRequestDto updateRequest = new CarRequestDto(
                "Tesla",
                "Model 3",
                2023,
                "Electric",
                "EV Motor",
                new BigDecimal("8.5"),
                new BigDecimal("3500.00"),
                new BigDecimal("2.5"),
                "https://new-image.com/tesla.png"
        );

        mockMvc.perform(put("/api/v1/cars/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is("Tesla")))
                .andExpect(jsonPath("$.model", is("Model 3")))
                .andExpect(jsonPath("$.fuelType", is("Electric")))
                .andExpect(jsonPath("$.engine", is("EV Motor")))
                .andExpect(jsonPath("$.pricePerMinute", is(8.5)))
                .andExpect(jsonPath("$.insurancePrice", is(2.5)))
                .andExpect(jsonPath("$.imageUrl", is("https://new-image.com/tesla.png")));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 7, 10})
    void testUpdateCar_MultipleValidIds_ShouldReturn200(long carId) throws Exception {
        CarRequestDto updateRequest = createVaidCarRequestDto();

        mockMvc.perform(put("/api/v1/cars/" + carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) carId)))
                .andExpect(jsonPath("$.brand", is("Dodge")));
    }
}

