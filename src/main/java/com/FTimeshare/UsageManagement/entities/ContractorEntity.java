package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Contractor")
@NoArgsConstructor

public class ContractorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractor_id")
    private int contractorID;

    @Column(name = "contractor_name")
    private String contractorName;

    @Column(name = "contractor_description")
    private String contractorDescription;

}
