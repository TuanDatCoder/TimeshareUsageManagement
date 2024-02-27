package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import com.FTimeshare.UsageManagement.exceptions.UserAlreadyExistsException;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.RoleRepository;
import jakarta.transaction.Transactional;
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



    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<AccountEntity> getAllUsers() {
        return accountRepository.findAll();
    }


    public AccountEntity findByAccountEmail(String acc_email){
        return accountRepository.findByAccEmail(acc_email);
    }

    public AccountEntity saveAccount(AccountEntity account) {
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

    public AccountEntity registerAccount(AccountEntity account) {
        if (accountRepository.existsByAccEmail(account.getAccEmail())){
            throw new UserAlreadyExistsException(account.getAccEmail() + " already exists");
        }
        account.setAccPassword(passwordEncoder.encode(account.getAccPassword()));
        System.out.println(account.getAccPassword());
        RoleEntity accRole = roleRepository.findByRoleName("ROLE_CUSTOMER");
        account.setRoleID(accRole);
        return accountRepository.save(account);
    }

    public List<AccountEntity> getAccount() {
        return accountRepository.findAll();
    }

    @Transactional
    public void deleteAccountByEmail(String email) {
        AccountEntity theAccount = getAccount(email);
        if (theAccount != null){
            accountRepository.deleteByAccEmail(email);
        }

    }

    public AccountEntity getAccount(String email) {
        return accountRepository.findByAccEmail(email);
    }





}