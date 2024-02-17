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
public class UserDto {
    private String userID;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private boolean userStatus;
    private Date userBirthday;
    private String roleID;

}
