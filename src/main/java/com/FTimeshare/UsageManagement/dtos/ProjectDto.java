package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private String projectID;
    private String projectName;
    private String projectDescription;
    private String projectBuildDate;
    private String projectArea;
    private String projectStatus;
    private String projectPicture;
    private String contractorID;
    private String projectTypeID;

}
