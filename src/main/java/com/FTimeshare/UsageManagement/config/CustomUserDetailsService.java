package com.FTimeshare.UsageManagement.config;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity account = accountService.findByAccountEmail(email);
        return CustomUserDetails.fromAccountEntityToCustomUserDetails(account);
    }


}
