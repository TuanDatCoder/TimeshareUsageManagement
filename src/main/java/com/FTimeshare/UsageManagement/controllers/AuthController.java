package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.config.jwt.JwtProvider;
import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.dtos.AuthRequest;
import com.FTimeshare.UsageManagement.dtos.AuthResponse;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
@RestController
public class AuthController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtProvider jwtProvider;

//    @Autowired
//    private AccountController accountController;
//    @PostMapping("/register")
//    public String registerAccount(@RequestBody @Valid AccountDto accountDto) {
//        AccountEntity account = new AccountEntity();
//        accountController.convertToEntity(accountDto);
//        accountService;
//        return "OK";
//    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        AccountEntity account = accountService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(account.getAccEmail());
        return new AuthResponse(token);
    }
}
