package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.exceptions.UserAlreadyExistsException;
import com.FTimeshare.UsageManagement.request.LoginRequest;
import com.FTimeshare.UsageManagement.response.JwtResponse;
import com.FTimeshare.UsageManagement.security.jwt.AuthTokenFilter;
import com.FTimeshare.UsageManagement.security.jwt.JwtUtils;
import com.FTimeshare.UsageManagement.security.user.TimeshareUserDetails;
import com.FTimeshare.UsageManagement.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthTokenFilter authTokenFilter;
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody AccountEntity accountEntity){
        try{
            accountService.registerAccount(accountEntity);
            return ResponseEntity.ok("Registration successful!");

        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        TimeshareUserDetails userDetails = (TimeshareUserDetails) authentication.getPrincipal();
        String roles = userDetails.getAuthorities().toString();
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwt,
                roles));
    }
}