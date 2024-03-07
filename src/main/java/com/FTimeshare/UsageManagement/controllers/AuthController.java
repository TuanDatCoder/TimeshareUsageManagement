package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.exceptions.UserAlreadyExistsException;
import com.FTimeshare.UsageManagement.request.LoginRequest;
import com.FTimeshare.UsageManagement.response.JwtResponse;
import com.FTimeshare.UsageManagement.security.jwt.AuthTokenFilter;
import com.FTimeshare.UsageManagement.security.jwt.JwtUtils;
import com.FTimeshare.UsageManagement.security.user.TimeshareUserDetails;
import com.FTimeshare.UsageManagement.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
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


//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        String token = authTokenFilter.parseJwt(request);
//        tokenBlacklist.addToBlacklist(token);
//
//        // Clear any session-related data if necessary
//
//        return ResponseEntity.ok("Logged out successfully");
//    }
//    @PostMapping("/logout")

//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        // Get the current authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null) {
//            // Invalidate the token
//            String token = request.getHeader("Authorization");
//            if (token != null) {
//                // If the token is blacklisted, it means the user is already logged out
//                if (TokenBlacklist.isBlacklisted(token)) {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is already logged out");
//                }
//
//                // Add the token to the blacklist
//                TokenBlacklist.add(token);
//
//                // Return a success message
//                return ResponseEntity.ok("User logged out successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
//        }
//    }
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