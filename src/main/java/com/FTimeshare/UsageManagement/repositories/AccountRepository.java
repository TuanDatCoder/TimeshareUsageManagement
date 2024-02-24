package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    @Query("SELECT u FROM AccountEntity u WHERE u.roleID.roleID = :roleId")
    List<AccountEntity> findAllByRoleID(@Param("roleId") int roleId);


    int countByRoleIDRoleName(String roleName);
    List<AccountEntity> findByRoleIDRoleName(String roleName);
    //List<AccountEntity> findByAccNameContainingIgnoreCaseAndRoleName(String accName, String roleName);
    @Query("SELECT DISTINCT a.accStatus FROM AccountEntity a")
    List<String> findAllStatus();

}

