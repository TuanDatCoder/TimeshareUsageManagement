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
public class NewsDto {
    private String newsId;
    private String newsTitle;
    private Date newsPost;
    private String newsContent;
    private String userId;
}
