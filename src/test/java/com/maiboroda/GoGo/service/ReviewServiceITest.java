package com.maiboroda.GoGo.service;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DataSet({"datasets/cars.yml", "datasets/reviews.yml"})
public class ReviewServiceITest extends AbstractIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void shouldReturnLastReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getLastReviews();
        assertNotNull(reviews, "Reviews should not be null");
        assertEquals(4, reviews.size(), "Should return 4 last reviews");
    }
    @Test
    public void shouldReturnAllReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
        assertNotNull(reviews, "Reviews should not be null");
        assertEquals(5, reviews.size(), "Should return all 5 reviews");
    }

    @Test
    public void shouldReturnEmptyList() {
        List<ReviewResponseDTO> reviews = reviewService.gerReviewList();
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.get(0).getReviewText().length() > 0);
    }
    @Test
    public void deleteReviewById() {
        reviewService.deleteReviewById(1L);
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
        assertNotNull(reviews, "Reviews should not be null");
        assertEquals(4, reviews.size(), "Should return 4 reviews after deletion");
    }
}
