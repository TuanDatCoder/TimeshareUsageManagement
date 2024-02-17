package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.UserDto;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @GetMapping("/customer")
    public ResponseEntity<List<UserDto>> getCustomerUsers() {
        return userController.getCustomerUsers();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<UserDto>> getOwnerUsers() {
        return userController.getOwnerUsers();
    }

    @PutMapping("/update-status/{userId}")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable String userId, @RequestBody UserDto userDto) {
        UserEntity updatedUser = userService.updateUserStatus(userId, userDto.isUserStatus());
        return ResponseEntity.ok(userController.convertToDto(updatedUser));
    }
}
