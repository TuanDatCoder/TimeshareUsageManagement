package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Contractor")
@NoArgsConstructor
@AllArgsConstructor
public class ContractorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String contractorID;

    @Column(name = "contractorName")
    private String contractorName;

    @Column(name = "contractorDescription")
    private String contractorDescription;

}
