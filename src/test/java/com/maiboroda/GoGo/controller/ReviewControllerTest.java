package com.maiboroda.GoGo.controller;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DBRider
@DataSet("datasets/reviews.yml")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReviewControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnLastReviews() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].reviewText").value("Супер сервіс, буду користуватися ще раз"))
                .andExpect(jsonPath("$[0].firstName").value("Остап"))
                .andExpect(jsonPath("$[0].town").value("Київ"))
                .andExpect(jsonPath("$[0].dateOfBirth").exists());
    }

    @Test
    public void returnAllReviews() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void returnRandomReview () throws Exception {
        mockMvc.perform(get("/api/reviews/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(4)));
    }
}
