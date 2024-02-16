package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ProjectType")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String projectTypeID;

    @Column(name = "projectTypeName")
    private String projectTypeName;

}
