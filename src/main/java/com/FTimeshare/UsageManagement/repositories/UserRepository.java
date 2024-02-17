package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
//    List<UserEntity> findByRole(String role);
}

//@Query(value = "SELECT * FROM [User] WHERE [userID] = ?1", nativeQuery = true)
//UserEntity findByUser(@Param("userID") String userID);