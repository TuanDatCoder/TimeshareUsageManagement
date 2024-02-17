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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public ResponseEntity<List<UserDto>> getAdminUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole("rol001");
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<UserDto>> getStaffUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole("rol004");
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<UserDto>> getCustomerUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole("rol002");
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<UserDto>> getOwnerUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole("rol013");
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    private List<UserDto> convertToDtoList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserID(userEntity.getUserID());
        userDto.setUserName(userEntity.getUserName());
        userDto.setUserPhone(userEntity.getUserPhone());
        userDto.setUserEmail(userEntity.getUserEmail());
        userDto.setUserPassword(userEntity.getUserPassword());
        userDto.setUserBirthday(userEntity.getUserBirthday());
        String roleID = (userEntity.getRoleID() != null) ? userEntity.getRoleID().getRoleID() : null;
        userDto.setRoleID(roleID);
        return userDto;
    }

}

