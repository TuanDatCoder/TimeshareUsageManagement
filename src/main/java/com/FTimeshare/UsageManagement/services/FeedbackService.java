package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
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

    private FeedbackDto convertToDto(FeedbackEntity feedbackEntity) {
        return new FeedbackDto(
                feedbackEntity.getFeedbackID(),
                feedbackEntity.getFeedbackCreateDate(),
                feedbackEntity.getFeedbackDetail(),
                feedbackEntity.getFeedbackStatus(),
                feedbackEntity.getBookingID().getBookingID()
        );
    }

    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
        FeedbackEntity feedbackEntity = convertToEntity(feedbackDto);
        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        return convertToDto(savedFeedback);
    }

    public FeedbackDto editFeedback(int feedbackID, FeedbackDto updatedFeedback) {
        // Tìm phản hồi cần chỉnh sửa trong cơ sở dữ liệu
        FeedbackEntity existingFeedback = feedbackRepository.findById(String.valueOf(feedbackID))
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + feedbackID));

        // Cập nhật thông tin của phản hồi
        existingFeedback.setFeedbackCreateDate(updatedFeedback.getFeedbackCreateDate());
        existingFeedback.setFeedbackDetail(updatedFeedback.getFeedbackDetail());
        existingFeedback.setFeedbackStatus(updatedFeedback.getFeedbackStatus());

        // Lưu cập nhật vào cơ sở dữ liệu
        FeedbackEntity savedFeedback = feedbackRepository.save(existingFeedback);

        // Chuyển đổi và trả về phiên bản cập nhật của phản hồi
        return convertToDto(savedFeedback);
    }

    public FeedbackDto deleteFeedback(int feedbackID) {
        // Tìm đặt phòng theo ID
        Optional<FeedbackEntity> bookingEntityOptional = feedbackRepository.findById(String.valueOf(feedbackID));

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

        // Assume that bookingID is an int in FeedbackDto
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingID(feedbackDto.getBookingID());

        // Set the bookingEntity to feedbackEntity
        feedbackEntity.setBookingID(bookingEntity);

        return feedbackEntity;
    }
}





