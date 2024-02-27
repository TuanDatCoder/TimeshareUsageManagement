package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private int accID;
    private String accName;
    private String accPhone;
    private String accEmail;
    private String accPassword;
    private String imgName;
    private byte[] imgData;
    private String accStatus;
    private Date accBirthday;
    private int roleID;
}
