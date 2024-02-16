package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface UserService {
    Optional<UserEntity> findUser(String id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity userEntity);
    UserEntity update(UserEntity userEntity);
    void delete(UserEntity userEntity);
}
