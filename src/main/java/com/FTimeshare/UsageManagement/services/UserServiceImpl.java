package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findUser(String id) {

        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {

        return userRepository.findAll();
    }

    @Override
    public UserEntity update(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }

    @Override
    public void delete(UserEntity userEntity) {

        userRepository.delete(userEntity);
    }

}