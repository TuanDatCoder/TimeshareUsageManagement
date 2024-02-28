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


    public String uploadImage(MultipartFile file,
                              String accName,
                              String accPhone,
                              String accEmail,
                              String accPassword,
                              String accStatus,
                              Date accBirthday,
                              int roleID) throws IOException {

        byte[] imgData = file.getBytes();

        RoleEntity roleEntity = roleRepository.findById(roleID)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " +roleID));


        AccountEntity accountEntity = accountRepository.save(AccountEntity.builder()
                .accName(accName)
                .accPhone(accPhone)
                .accEmail(accEmail)
                .accPassword(accPassword)
                .imgName(file.getOriginalFilename())
                .imgData(imgData)
                .accStatus(accStatus)
                .accBirthday(accBirthday)
                .roleID(roleEntity)
                .build());

        return "File uploaded successfully: " + file.getOriginalFilename();
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