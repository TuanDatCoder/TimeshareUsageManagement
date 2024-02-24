package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<AccountEntity> getAllUsers() {
        return accountRepository.findAll();
    }

    public List<AccountEntity> getUsersByRole(int roleId) {
        return accountRepository.findAllByRoleID(roleId);
    }

    public AccountEntity findByAccountEmail(String acc_email){
        return accountRepository.findByAccEmail(acc_email);
    }

    public AccountEntity saveUser(AccountEntity account) {
        RoleEntity userRole = roleRepository.findByRoleName("ROLE_CUSTOMER");
        account.setRoleID(userRole);
        account.setAccPassword(passwordEncoder.encode(account.getAccPassword()));
        return accountRepository.save(account);
    }

    public AccountEntity findByEmailAndPassword(String email, String password){
        AccountEntity account = findByAccountEmail(email);
        if (account!=null){
            if(passwordEncoder.matches(password, account.getAccPassword()))
                return account;
        }
        return null;
    }

    //delete
    public void deleteUserById(int userId) {
        accountRepository.deleteById(userId);
    }

    //save user


    // update
    public AccountEntity updateUserStatus(int userId, String newUserStatus) {
        AccountEntity userEntity = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userEntity.setAccStatus(newUserStatus);
        return accountRepository.save(userEntity);
    }

}