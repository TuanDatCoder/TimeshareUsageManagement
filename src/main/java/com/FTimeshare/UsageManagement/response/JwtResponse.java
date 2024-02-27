package com.FTimeshare.UsageManagement.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class JwtResponse {
    private int id;
    private String email;
    private String token;
    private String type = "Bearer";
    private String role;

    public JwtResponse(int id, String email, String token, String role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
    }
}