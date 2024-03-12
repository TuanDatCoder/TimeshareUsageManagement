package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import com.FTimeshare.UsageManagement.exceptions.UserAlreadyExistsException;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void deleteAccountsByAccIDs(List<Integer> accIDs) {
        accountRepository.deleteAllByAccIDIn(accIDs);
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
    public List<AccountEntity> getAccountsByStatus(String status) {
        return accountRepository.findByAccStatus(status);
    }
    public void statusAccount(int accID, String Status) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(accID);
        if (optionalAccount.isPresent()) {
            AccountEntity account = optionalAccount.get();
            account.setAccStatus(Status);
            accountRepository.save(account);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại với ID: " + accID);
        }
    }
    public String uploadImage(MultipartFile file,
                              String accName,
                              String accPhone,
                              String accEmail,
                              String accPassword,
                              String accStatus,
                              Date accBirthday,
                              int roleID) throws IOException {

        RoleEntity roleEntity = roleRepository.findById(roleID)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " +roleID));
        if (accountRepository.existsByAccEmail(accEmail)){
            return accEmail + " already exists";
        }

        AccountEntity accountEntity = accountRepository.save(AccountEntity.builder()
                .accName(accName)
                .accPhone(accPhone)
                .accEmail(accEmail)
                .accPassword(passwordEncoder.encode(accPassword))
                .imgName(file.getOriginalFilename())
                .imgData(ImageService.compressImage(file.getBytes()))
                .accStatus(accStatus)
                .accBirthday(accBirthday)
                .roleID(roleEntity)
                .build());

        return "File uploaded successfully: " + file.getOriginalFilename();
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

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


    public byte[] downloadImage(String fileName){
        Optional<AccountEntity> dbImageData = accountRepository.findByImgName(fileName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }
    public AccountEntity getAccountById(int accID) {
        Optional<AccountEntity> accountOptional = accountRepository.findById(accID);
        return accountOptional.orElse(null);
    }


    //edit feedback
    public AccountDto editAccount(int accountID, AccountDto updatedAccount, MultipartFile file) throws IOException {
        AccountEntity existingAccount = accountRepository.findById(accountID)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountID));

        existingAccount.setAccName(updatedAccount.getAccName());
        existingAccount.setAccPhone(updatedAccount.getAccPhone());
        existingAccount.setAccEmail(updatedAccount.getAccEmail());
        existingAccount.setAccPassword(passwordEncoder.encode(updatedAccount.getAccPassword()));
        existingAccount.setImgName(file.getOriginalFilename());
        existingAccount.setImgData(ImageService.compressImage(file.getBytes()));
        existingAccount.setAccStatus(updatedAccount.getAccStatus());
        existingAccount.setAccBirthday(updatedAccount.getAccBirthday());

        AccountEntity savedAccount = accountRepository.save(existingAccount);
        return convertToDto(savedAccount);
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

    public AccountDto convertToDto(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccID(accountEntity.getAccID());
        accountDto.setAccName(accountEntity.getAccName());
        accountDto.setAccPhone(accountEntity.getAccPhone());
        accountDto.setAccEmail(accountEntity.getAccEmail());
        accountDto.setAccPassword(accountEntity.getAccPassword());
        accountDto.setAccBirthday(accountEntity.getAccBirthday());
        accountDto.setImgName( "http://localhost:8080/api/users/viewImg/"+ accountEntity.getImgName());
        accountDto.setImgData(new byte[0]);
        int roleID = 0; // Giá trị mặc định nếu không tìm thấy roleID
        if (accountEntity.getRoleID() != null) {
            // Lấy ID của vai trò từ đối tượng RoleEntity và gán cho roleID
            roleID = accountEntity.getRoleID().getRoleID(); // Giả sử ID của vai trò là một số nguyên
        }
        accountDto.setRoleID(roleID);

        return accountDto;
    }
}