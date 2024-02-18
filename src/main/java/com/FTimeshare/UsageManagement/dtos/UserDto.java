package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private int userID;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private LocalDateTime userBirthday;
    private int roleID;

}
