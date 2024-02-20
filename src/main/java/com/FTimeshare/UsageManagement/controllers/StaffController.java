package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @GetMapping("/customer")
    public ResponseEntity<List<AccountDto>> getCustomerUsers() {
        return accountController.getCustomerUsers();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<AccountDto>> getOwnerUsers() {
        return accountController.getOwnerUsers();
    }

    @PutMapping("/update-status/{userId}")
    public ResponseEntity<AccountDto> updateAccStatus(@PathVariable int accId, @RequestBody AccountDto accDto) {
        AccountEntity updatedAcc= accountService.updateUserStatus(accId, accDto.getAccStatus());
        return ResponseEntity.ok(accountController.convertToDto(updatedAcc));
    }
}

