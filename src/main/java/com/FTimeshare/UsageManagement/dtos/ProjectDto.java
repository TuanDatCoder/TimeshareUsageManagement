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
    private String projectPicture;
    private int contractorID;
    private int projectTypeID;

}
