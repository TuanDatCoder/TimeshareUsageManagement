package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> getAllFeedback();

    public FeedbackDto submitFeedback(FeedbackDto feedbackDto);
}
