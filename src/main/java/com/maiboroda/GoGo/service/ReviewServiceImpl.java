package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import com.maiboroda.GoGo.entity.Review;
import com.maiboroda.GoGo.mapper.ReviewMapper;
import com.maiboroda.GoGo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
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

    @Override
    public List<ReviewResponseDTO> gerReviewList() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.toDtoList(reviews);
    }

    @Override
    public List<ReviewResponseDTO> getRandomReviews() {
        if (randomNumberReview < 0) {
            throw new IllegalArgumentException("Invalid Number, it must be positive");
        }
        List<Review> reviews = reviewRepository.findLastReviews(randomNumberReview);
        if (randomNumberReview > reviews.size()) {
            throw new IllegalArgumentException("Invalid Number, it must be from 1 to " + reviews.size());
        }
        log.info("Successfully add {} random cars", reviews.size());
        return reviewMapper.toDtoList(reviews);
    }

    @Transactional
    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }


}
