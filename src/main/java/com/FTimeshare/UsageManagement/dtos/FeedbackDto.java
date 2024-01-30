package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private String feedbackID;
    private Date feedbackCreateDate;
    private String feedbackDetail;
    private boolean feedbackStatus;
    private String bookingID;
}
