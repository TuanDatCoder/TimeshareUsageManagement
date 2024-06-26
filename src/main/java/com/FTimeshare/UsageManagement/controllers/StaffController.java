package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
//@CrossOrigin(origins = "https://pass-timeshare.vercel.app")
//@CrossOrigin(origins = "https://pass-timeshare-tuandat-frontends-projects.vercel.app")
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @PutMapping("/update-status/{userId}")
    public ResponseEntity<AccountDto> updateAccStatus(@PathVariable int accId, @RequestBody AccountDto accDto) {
        AccountEntity updatedAcc= accountService.updateUserStatus(accId, accDto.getAccStatus());
        return ResponseEntity.ok(accountController.convertToDto(updatedAcc));
    }
}

