package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Review;
import com.maiboroda.GoGo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Value("${gogo.settings.random-number-review}")
    private int randomNumberReview;

    @Override
    public List<Review> getLastReviews() {
        return reviewRepository.findLastReviews(randomNumberReview);
    }
}
