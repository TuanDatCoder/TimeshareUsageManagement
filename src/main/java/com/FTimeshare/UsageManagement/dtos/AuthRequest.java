package com.FTimeshare.UsageManagement.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
