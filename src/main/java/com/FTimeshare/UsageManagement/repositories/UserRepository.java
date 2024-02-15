package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, String> {
   // List<UserEntity> findAll();

}
