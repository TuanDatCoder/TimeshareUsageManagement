package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountEntity> getAllUsers() {
        return accountRepository.findAll();
    }

    public List<AccountEntity> getUsersByRole(int roleId) {
        return accountRepository.findAllByRoleID(roleId);
    }


    //delete
    public void deleteUserById(int userId) {
        accountRepository.deleteById(userId);
    }


    // update
    public AccountEntity updateUserStatus(int accID, String newAccountStatus) {
        AccountEntity accountEntity = accountRepository.findById(accID)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + accID));

        // Cập nhật trạng thái mới cho người dùng
        accountEntity.setAccStatus(newAccountStatus);

        // Lưu thay đổi vào cơ sở dữ liệu
        return accountRepository.save(accountEntity);
    }

    public int countUsersByRoleName(String roleName) {
        return accountRepository.countByRoleIDRoleName(roleName);
    }
    public List<AccountEntity> getUsersByRoleName(String roleName) {
        return accountRepository.findByRoleIDRoleName(roleName);
    }
//    public List<AccountEntity> findByAccNameContainingIgnoreCaseAndRoleName(String accName, String roleName) {
//        return accountRepository.findByAccNameContainingIgnoreCaseAndRoleName(accName, roleName);
//    }

    public List<String> getAllStatus() {
        return accountRepository.findAllStatus();
    }


}