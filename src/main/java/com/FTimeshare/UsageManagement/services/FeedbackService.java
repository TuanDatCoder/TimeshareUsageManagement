package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;


    public List<FeedbackDto> getAllFeedback() {
        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAll();
        return feedbackEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<FeedbackDto> viewFeedbackByProductID(int productID) {
        List<FeedbackEntity> feedbackEntities = feedbackRepository.findByProductID_ProductID(productID);
        return feedbackEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FeedbackDto convertToDto(FeedbackEntity feedbackEntity) {
        return new FeedbackDto(
                feedbackEntity.getFeedbackID(),
                feedbackEntity.getFeedbackCreateDate(),
                feedbackEntity.getFeedbackDetail(),
                feedbackEntity.getFeedbackStatus(),
                feedbackEntity.getFeedbackRating(),
                feedbackEntity.getBookingID().getBookingID(),
                feedbackEntity.getProductID().getProductID()
        );
    }

    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
        FeedbackEntity feedbackEntity = convertToEntity(feedbackDto);
        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        return convertToDto(savedFeedback);
    }

    public FeedbackDto editFeedback(int feedbackID, FeedbackDto updatedFeedback) {
        // Tìm phản hồi cần chỉnh sửa trong cơ sở dữ liệu
        FeedbackEntity existingFeedback = feedbackRepository.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + feedbackID));

        existingFeedback.setFeedbackCreateDate(updatedFeedback.getFeedbackCreateDate());
        existingFeedback.setFeedbackDetail(updatedFeedback.getFeedbackDetail());
        existingFeedback.setFeedbackStatus(updatedFeedback.getFeedbackStatus());
        existingFeedback.setFeedbackRating(updatedFeedback.getFeedbackRating());

        FeedbackEntity savedFeedback = feedbackRepository.save(existingFeedback);

        return convertToDto(savedFeedback);
    }

    public FeedbackDto deleteFeedback(int feedbackID) {
        Optional<FeedbackEntity> bookingEntityOptional = feedbackRepository.findById(feedbackID);

        if (bookingEntityOptional.isPresent()) {
            FeedbackEntity feedbackEntity = bookingEntityOptional.get();

            feedbackRepository.delete(feedbackEntity);
            return convertToDto(feedbackEntity);
        } else {
            return null;
        }
    }


    private FeedbackEntity convertToEntity(FeedbackDto feedbackDto) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setFeedbackID(feedbackDto.getFeedbackID());
        feedbackEntity.setFeedbackCreateDate(feedbackDto.getFeedbackCreateDate());
        feedbackEntity.setFeedbackDetail(feedbackDto.getFeedbackDetail());
        feedbackEntity.setFeedbackStatus(feedbackDto.getFeedbackStatus());
        feedbackEntity.setFeedbackRating(feedbackDto.getFeedbackRating());

        BookingEntity bookingEntity = new BookingEntity();
        ProductEntity productEntity = new ProductEntity();
        bookingEntity.setBookingID(feedbackDto.getBookingID());
        productEntity.setProductID(feedbackDto.getProductID());

        feedbackEntity.setBookingID(bookingEntity);
        feedbackEntity.setProductID(productEntity);

        return feedbackEntity;
    }

    public List<Float> getFeedbackRatingByProductId(int productID) {
        List<FeedbackEntity> feedbackRatings = feedbackRepository.findByProductID_ProductID(productID);

        return feedbackRatings.stream()
                .map(FeedbackEntity::getFeedbackRating)
                .collect(Collectors.toList());
    }

        public Float getAverageFeedbackRatingByProductId(int productID) {
            List<Float> feedbackRatings = feedbackRepository.findBookingRatingsByProductID(productID);

            if (feedbackRatings.isEmpty()) {
                return null;
            }

            double sum = feedbackRatings.stream()
                    .mapToDouble(Float::doubleValue)
                    .sum();

            return (float) (sum / feedbackRatings.size());
        }
}