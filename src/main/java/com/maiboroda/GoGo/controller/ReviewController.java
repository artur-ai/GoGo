package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import com.maiboroda.GoGo.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
@AllArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping
    public List <ReviewResponseDTO> getLastReviews(){
            return reviewService.getLastReviews();
    }

    @GetMapping ("/random")
    public ResponseEntity<List<ReviewResponseDTO>> getRandomReviews() {
        try {
            List<ReviewResponseDTO> review = reviewService.getRandomReviews();
            log.info("Retrieved {} random cars", review.size());
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }}