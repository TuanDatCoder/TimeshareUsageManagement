package com.FTimeshare.UsageManagement.service;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public List<UserEntity> getUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String userID) {
        UserEntity theUser = getUser(userID);
        if (theUser != null){
            userRepository.deleteById(userID);
        }
    }

    @Override
    public UserEntity getUser(String userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new NullPointerException("User not found"));
    }
}
