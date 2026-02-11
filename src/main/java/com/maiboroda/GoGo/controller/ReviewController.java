package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import com.maiboroda.GoGo.entity.Review;
import com.maiboroda.GoGo.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping
    public List <ReviewResponseDTO> getLastReviews(){
            return reviewService.getLastReviews();
    }
}
