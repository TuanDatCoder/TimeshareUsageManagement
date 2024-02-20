package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import com.FTimeshare.UsageManagement.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;


    @Override
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


    @Override
    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
            FeedbackEntity feedbackEntity = convertToEntity(feedbackDto);
            FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
            return convertToDto(savedFeedback);
        }

    @Override
    public FeedbackDto editFeedback(int feedbackID, FeedbackDto updatedFeedback) {
        return null;
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





