package com.maiboroda.GoGo.repository;

import com.maiboroda.GoGo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM reviews ORDER BY RANDOM: count", nativeQuery = true)
    List<Review> findRandomReview(@Param("count")int count);
}
