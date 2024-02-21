package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> getAllFeedback();

    FeedbackDto submitFeedback(FeedbackDto feedbackDto);

    FeedbackDto editFeedback(int feedbackID, FeedbackDto updatedFeedback);

    FeedbackDto deleteFeedback(int feedbackID);
}
