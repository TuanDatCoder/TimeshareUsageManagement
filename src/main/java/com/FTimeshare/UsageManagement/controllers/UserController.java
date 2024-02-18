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
        List<UserEntity> userEntities = userService.getUsersByRole(1);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<UserDto>> getStaffUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole(4);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<UserDto>> getCustomerUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole(2);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<UserDto>> getOwnerUsers() {
        List<UserEntity> userEntities = userService.getUsersByRole(3);
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

        int roleID = 0; // Giá trị mặc định nếu không tìm thấy roleID
        if (userEntity.getRoleID() != null) {
            // Lấy ID của vai trò từ đối tượng RoleEntity và gán cho roleID
            roleID = userEntity.getRoleID().getRoleID(); // Giả sử ID của vai trò là một số nguyên
        }
        userDto.setRoleID(roleID);

        return userDto;
    }


}

