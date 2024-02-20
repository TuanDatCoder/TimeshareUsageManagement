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



//    @Override
//    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
//        return null;
//    }

//    @Override
//    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
//        // Convert DTO to Entity and save
//        Feedback savedFeedback = feedbackRepository.save(convertToFeedbackEntity(feedbackDto));
//        // Convert the saved Entity back to DTO and return
//        return convertToDto(savedFeedback);
//    }
//
//@Override
//public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
//    // Check if the booking exists
//    if (!bookingRepository.existsById(feedbackDto.getBookingID())) {
//        // Handle the case where the booking does not exist
//        // You may throw an exception or return an appropriate response
//        // For simplicity, let's assume you return null in case of a non-existing booking
//        return null;
//    }
//
//    // Convert DTO to Entity and save
//    FeedbackEntity savedFeedbackEntity = feedbackRepository.save(convertToFeedbackEntity(feedbackDto));
//
//    // Convert the saved Entity back to DTO and return
//    return convertToFeedbackDto(savedFeedbackEntity);
//}
//
//    private FeedbackEntity convertToFeedbackEntity(FeedbackDto feedbackDto) {
//        FeedbackEntity feedbackEntity = new FeedbackEntity();
//        feedbackEntity.setFeedbackCreateDate(feedbackDto.getFeedbackCreateDate());
//        feedbackEntity.setFeedbackDetail(feedbackDto.getFeedbackDetail());
//        feedbackEntity.setFeedbackStatus(feedbackDto.isFeedbackStatus());
//
//        // Fetch the BookingEntity from the database using the provided bookingID
//        feedbackEntity.setBookingID(bookingRepository.findById(feedbackDto.getBookingID()).orElse(null));
//
//        return feedbackEntity;
//    }
//
//    private FeedbackDto convertToFeedbackDto(FeedbackEntity feedbackEntity) {
//        FeedbackDto feedbackDto = new FeedbackDto();
//        feedbackDto.setFeedbackID(feedbackEntity.getFeedbackID());
//        feedbackDto.setFeedbackCreateDate(feedbackEntity.getFeedbackCreateDate());
//        feedbackDto.setFeedbackDetail(feedbackEntity.getFeedbackDetail());
//        feedbackDto.setFeedbackStatus(feedbackEntity.isFeedbackStatus());
//        feedbackDto.setBookingID(feedbackEntity.getBookingID().getBookingID());
//
//        return feedbackDto;
//    }


