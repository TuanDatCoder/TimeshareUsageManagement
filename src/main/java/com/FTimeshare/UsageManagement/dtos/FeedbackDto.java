package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private int feedbackID;
    private LocalDateTime feedbackCreateDate ;
    private String feedbackDetail;
    private String feedbackStatus;
    private float feedbackRating;
    private int bookingID;
    private int productID;
}
