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

        // Cập nhật thông tin của phản hồi
        existingFeedback.setFeedbackCreateDate(updatedFeedback.getFeedbackCreateDate());
        existingFeedback.setFeedbackDetail(updatedFeedback.getFeedbackDetail());
        existingFeedback.setFeedbackStatus(updatedFeedback.getFeedbackStatus());
        existingFeedback.setFeedbackRating(updatedFeedback.getFeedbackRating());

        // Lưu cập nhật vào cơ sở dữ liệu
        FeedbackEntity savedFeedback = feedbackRepository.save(existingFeedback);

        // Chuyển đổi và trả về phiên bản cập nhật của phản hồi
        return convertToDto(savedFeedback);
    }

    public FeedbackDto deleteFeedback(int feedbackID) {
        // Tìm đặt phòng theo ID
        Optional<FeedbackEntity> bookingEntityOptional = feedbackRepository.findById(feedbackID);

        if (bookingEntityOptional.isPresent()) {
            FeedbackEntity feedbackEntity = bookingEntityOptional.get();

            // Kiểm tra xem người dùng có quyền xóa đặt phòng hay không (tùy thuộc vào logic của bạn)

            // Xóa đặt phòng
            feedbackRepository.delete(feedbackEntity);

            // Chuyển đổi và trả về DTO của đặt phòng đã xóa
            return convertToDto(feedbackEntity);
        } else {
            // Xử lý trường hợp không tìm thấy đặt phòng
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

        // Assume that bookingID is an int in FeedbackDto
        BookingEntity bookingEntity = new BookingEntity();
        ProductEntity productEntity = new ProductEntity();
        bookingEntity.setBookingID(feedbackDto.getBookingID());
        productEntity.setProductID(feedbackDto.getProductID());

        // Set the bookingEntity to feedbackEntity
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
                return null; // or handle the case where there are no ratings
            }

            double sum = feedbackRatings.stream()
                    .mapToDouble(Float::doubleValue)
                    .sum();

            return (float) (sum / feedbackRatings.size());
        }
}