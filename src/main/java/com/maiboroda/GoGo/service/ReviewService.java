package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.ReviewResponseDTO;


import java.util.List;

public interface ReviewService{
    List<ReviewResponseDTO> getLastReviews();
}
