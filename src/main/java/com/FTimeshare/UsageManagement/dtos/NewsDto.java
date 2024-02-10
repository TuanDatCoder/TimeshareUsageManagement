package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
    private String NewsID;
    private String newsTitle;
    private LocalDateTime newsPost;
    private String newsContent;
    private String Userid;

}
