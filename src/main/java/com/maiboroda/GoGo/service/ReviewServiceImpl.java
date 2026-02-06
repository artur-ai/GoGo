package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Review;
import com.maiboroda.GoGo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getLastReviews(int count) {
        return reviewRepository.findLastReviews(4);
    }
}
