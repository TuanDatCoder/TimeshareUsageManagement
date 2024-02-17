package com.FTimeshare.UsageManagement.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Account")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private String userID;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_birthday")
    private Date userBirthday;

    @Column(name="user_status")
    private boolean userStatus;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleEntity roleID;


}
