package com.maiboroda.GoGo.repository;

import com.maiboroda.GoGo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT id, user_id, review_text FROM reviews r ORDER BY r.id DESC LIMIT :count", nativeQuery = true)
    List<Review> findLastReviews(@Param("count") int count);
}
