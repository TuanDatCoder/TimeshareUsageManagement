package com.FTimeshare.UsageManagement.service;

import com.FTimeshare.UsageManagement.dtos.UserDto;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
