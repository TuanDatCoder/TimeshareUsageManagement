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
public class ProjectDto {
    private int projectID;
    private String projectName;
    private String projectDescription;
    private LocalDateTime projectBuildDate;
    private String projectArea;
    private String projectStatus;
    private String imgName;
    private byte[] imgData;
    private int contractorID;

}
