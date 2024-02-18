package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getUsersByRole(int roleId) {
        return userRepository.findAllByRoleID(roleId);
    }


    // update
//    public UserEntity updateUserStatus(int userId, String newUserStatus) {
//        UserEntity userEntity = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//
//        // Cập nhật trạng thái mới cho người dùng
//        userEntity.setUserStatus(newUserStatus);
//
//        // Lưu thay đổi vào cơ sở dữ liệu
//        return userRepository.save(userEntity);
//    }

}