package com.FTimeshare.UsageManagement.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roleID;

    @Column(name="roleName")
    private String roleName;

    @Column(name="roleDescription")
    private String roleDescription;

}
