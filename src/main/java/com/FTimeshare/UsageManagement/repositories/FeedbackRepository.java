package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository <FeedbackEntity, Integer> {
    List<FeedbackEntity> findByProductID_ProductID(int productID);

    @Query("SELECT b.feedbackRating FROM FeedbackEntity b WHERE b.productID.productID = :productID")
    List<Float> findBookingRatingsByProductID(int productID);
}
