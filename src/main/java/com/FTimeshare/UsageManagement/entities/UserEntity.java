package com.FTimeshare.UsageManagement.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userID;

    @Column(name="userName")
    private String userName;

    @Column(name="userPhone")
    private String userPhone;

    @Column(name="userEmail")
    private String userEmail;

    @Column(name="userPassword")
    private String userPassword;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roleID")
    private RoleEntity role;

}
