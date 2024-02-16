package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ContractorEntity;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByRoleId(String roleID);
}
