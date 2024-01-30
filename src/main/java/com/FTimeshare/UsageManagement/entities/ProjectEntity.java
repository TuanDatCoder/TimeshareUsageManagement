package com.FTimeshare.UsageManagement.entities;

import com.FTimeshare.UsageManagement.entities.ContractorEntity;
import com.FTimeshare.UsageManagement.entities.ProjectTypeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Project")
@NoArgsConstructor

public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String projectID;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "projectDescription")
    private String projectDescription;

    @Column(name = "projectBuildDate")
    private String projectBuildDate;

    @Column(name = "projectArea")
    private String projectArea;

    @Column(name = "projectStatus")
    private String projectStatus;

    @Column(name = "projectPicture")
    private String projectPicture;

    @ManyToOne
    @JoinColumn(name = "contractorID", referencedColumnName = "contractorID")
    private ContractorEntity contractorID;

    @ManyToOne
    @JoinColumn(name = "projectTypeID", referencedColumnName = "projectTypeID")
    private ProjectTypeEntity projectTypeID;


}
