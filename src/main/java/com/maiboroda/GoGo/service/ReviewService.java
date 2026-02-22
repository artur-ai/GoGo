package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface ReviewService{
    List<ReviewResponseDTO> getLastReviews();
    List<ReviewResponseDTO> getAllReviews();
    List<ReviewResponseDTO> gerReviewList();
    List<ReviewResponseDTO> getRandomReviews();
    @Transactional
    void deleteReviewById(Long id);
}
