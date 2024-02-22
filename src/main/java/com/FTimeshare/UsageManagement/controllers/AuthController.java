//package com.FTimeshare.UsageManagement.controllers;
//
//import com.FTimeshare.UsageManagement.dtos.AccountDto;
//import com.FTimeshare.UsageManagement.dtos.LoginDto;
//import com.FTimeshare.UsageManagement.dtos.SignUpDto;
//import com.FTimeshare.UsageManagement.entities.AccountEntity;
//import com.FTimeshare.UsageManagement.entities.RoleEntity;
//import com.FTimeshare.UsageManagement.repositories.AccountRepository;
//import com.FTimeshare.UsageManagement.repositories.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody AccountDto accountUpDto){
//
//        // add check for username exists in a DB
//        if(accountRepository.existsByAccName(accountUpDto.getAccName())){
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if(accountRepository.existsByAccEmail(accountUpDto.getAccEmail())){
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // create user object
//        AccountEntity account = new AccountEntity();
//        account.setAccName(accountUpDto.getAccName());
//        account.setAccImg(accountUpDto.getAccImg());
//        account.setAccPhone(accountUpDto.getAccPhone());
//        account.setAccBirthday(accountUpDto.getAccBirthday());
//        account.setAccStatus(accountUpDto.getAccStatus());
//        account.setAccEmail(accountUpDto.getAccEmail());
//        account.setAccImg(accountUpDto.getAccImg());
//        account.setAccPassword(passwordEncoder.encode(accountUpDto.getAccPassword()));
//
//        RoleEntity role = roleRepository.findByRoleName("Customer").get();
//        account.setRoleID(role);
//
//        accountRepository.save(account);
//
//        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
//
//    }
//}
