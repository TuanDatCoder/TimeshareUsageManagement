package com.FTimeshare.UsageManagement.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
