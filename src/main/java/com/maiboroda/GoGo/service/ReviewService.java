package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Review;

import java.util.List;

public interface ReviewService{
    List<Review> getLastReviews();
}
