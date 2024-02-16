package com.FTimeshare.UsageManagement.service;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
//    UserEntity registerUser(UserEntity user);
    List<UserEntity> getUsers();
    void deleteUser(String email);
    UserEntity getUser(String email);
}
