package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    @Query("SELECT u FROM AccountEntity u WHERE u.roleID.roleID = :roleId")
    List<AccountEntity> findAllByRoleID(@Param("roleId") int roleId);

//    @Query("SELECT u FROM AccountEntity u WHERE u.accEmail = :accEmail")
//    AccountEntity findByAccEmail(String accEmail);

    @Query("SELECT u FROM AccountEntity u WHERE u.accEmail = :email")
    AccountEntity findByAccEmail(String email);
    AccountEntity findByAccName(String username);
    Optional<AccountEntity> findByAccNameOrAccEmail(String accName, String accEmail);
    void deleteByAccEmail(String email);
    Boolean existsByAccName(String accName);
    Boolean existsByAccEmail(String accEmail);


    int countByRoleIDRoleName(String roleName);
    List<AccountEntity> findByRoleIDRoleName(String roleName);
    //List<AccountEntity> findByAccNameContainingIgnoreCaseAndRoleName(String accName, String roleName);
    @Query("SELECT DISTINCT a.accStatus FROM AccountEntity a")
    List<String> findAllStatus();

    Optional<AccountEntity> findByImgName(String fileName);

    List<AccountEntity> findByAccStatus(String status);

    void deleteAllByAccIDIn(List<Integer> accIDs);
}

