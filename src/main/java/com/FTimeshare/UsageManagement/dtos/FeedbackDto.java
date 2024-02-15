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
    private String feedbackID;
    private LocalDateTime feedbackCreateDate;
    private String feedbackDetail;
    private boolean feedbackStatus;
    private String bookingID;
}
