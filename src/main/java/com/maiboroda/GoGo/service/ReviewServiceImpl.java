package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import com.maiboroda.GoGo.entity.Review;
import com.maiboroda.GoGo.mapper.ReviewMapper;
import com.maiboroda.GoGo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Value("${gogo.settings.random-number-review}")
    private int randomNumberReview;
    private final ReviewMapper reviewMapper;


    @Override
    public List<ReviewResponseDTO> getLastReviews() {
        List<Review> reviews = reviewRepository.findLastReviews(randomNumberReview);
        return reviewMapper.toDtoList(reviews);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.toDtoList(reviews);
    }
}
