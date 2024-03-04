package com.FTimeshare.UsageManagement.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="Account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"acc_name"}),
        @UniqueConstraint(columnNames = {"acc_email"})
})
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="acc_id")
    private int accID;

    @Column(name="acc_name")
    private String accName;

    @Column(name="acc_phone")
    private String accPhone;

    @Column(name="acc_email")
    private String accEmail;

    @Column(name="acc_password")
    private String accPassword;

    @Column(name="acc_birthday")
    private Date accBirthday;

    @Column(name="img_name")
    private String imgName;

    @Lob
    @Column(name="img_data")
    private byte[] imgData;

    @Column(name="acc_status")
    private String accStatus;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleEntity roleID;

}
