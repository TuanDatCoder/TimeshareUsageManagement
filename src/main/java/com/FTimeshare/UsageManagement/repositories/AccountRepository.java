package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
   // List<UserEntity> findAll();

}
