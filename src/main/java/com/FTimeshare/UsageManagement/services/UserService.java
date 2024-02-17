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

    public List<UserEntity> getUsersByRole(String roleId) {
        return userRepository.findAllByRoleID(roleId);
    }


}