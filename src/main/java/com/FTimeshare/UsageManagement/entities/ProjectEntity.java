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
    @Column(name = "project_id")
    private int projectID;

    @Column(name = "project_Name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_build_date")
    private LocalDateTime projectBuildDate;

    @Column(name = "project_area")
    private String projectArea;

    @Column(name = "project_status")
    private String projectStatus;

    @Column(name = "img_name")
    private String imgName;

    @Lob
    @Column(name = "img_data")
    private byte[] imgData;

    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "contractor_id")
    private ContractorEntity contractorID;


}
