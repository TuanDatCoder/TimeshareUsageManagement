package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

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
                .imgData(ImageService.compressImage(file.getBytes()))
                .accStatus(accStatus)
                .accBirthday(accBirthday)
                .roleID(roleEntity)
                .build());

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName){
        Optional<AccountEntity> dbImageData = accountRepository.findByImgName(fileName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }
    public AccountEntity getAccountById(int accID) {
        Optional<AccountEntity> accountOptional = accountRepository.findById(accID);
        return accountOptional.orElse(null);
    }

    public List<AccountDto> getAllAccounts() {
            List<AccountEntity> accounts = accountRepository.findAll();
            return accounts.stream()
                    .map(accountEntity -> new AccountDto(
                            accountEntity.getAccID(),
                            accountEntity.getAccName(),
                            accountEntity.getAccPhone(),
                            accountEntity.getAccEmail(),
                            accountEntity.getAccPassword(),
                            "http://localhost:8080/api/users/viewImg/" + accountEntity.getImgName(),  // Thêm imgName vào đường dẫn
                            new byte[0],
                            accountEntity.getAccStatus(),
                            accountEntity.getAccBirthday(),
                            accountEntity.getRoleID().getRoleID()))
                    .collect(Collectors.toList());
        }


}