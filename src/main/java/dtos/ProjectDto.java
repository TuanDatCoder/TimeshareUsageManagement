package dtos;

import lombok.Data;

@Data
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
