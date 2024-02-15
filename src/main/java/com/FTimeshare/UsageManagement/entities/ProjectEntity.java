package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Project")
@Data
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
    private LocalDateTime projectBuildDate;

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
