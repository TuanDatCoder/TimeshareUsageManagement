package com.FTimeshare.UsageManagement.security.user;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TimeshareUserDetailsService implements UserDetailsService {
    public final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findByAccEmail(email);
//               .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return TimeshareUserDetails.buildUserDetails(account);
    }
}